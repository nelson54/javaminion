package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.MatchRepository;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;
import com.github.nelson54.dominion.persistence.entities.match.MatchEntity;
import com.github.nelson54.dominion.persistence.entities.match.PlayerScoreEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private final MatchRepository matchRepository;
    private final CommandService commandService;
    private final EloService eloService;

    private GameFactory gameFactory;

    public MatchService(
            MatchRepository matchRepository,
            CommandService commandService,
            EloService eloService) {

        this.commandService = commandService;
        this.matchRepository = matchRepository;
        this.eloService = eloService;
    }

    public List<Match> findByStateIn(List<MatchState> states) {
        return StreamSupport
                .stream(matchRepository.findByStateIn(states).spliterator(), false)
                .map(MatchEntity::toMatch)
                .collect(Collectors.toList());
    }

    public List<Match> all() {
        return StreamSupport
                .stream(matchRepository.findAll().spliterator(), false)
                .map(MatchEntity::toMatch)
                .collect(Collectors.toList());
    }

    public Optional<Game> getGame(Long matchId) {
        return matchRepository
                .findById(matchId)
                .map(MatchEntity::toMatch)
                .map(gameFactory::createGame)
                .map(game -> {
                    game.setRebuilding(true);
                    return game;
                })
                .map(this::applyCommands)
                .map(game -> {
                    game.setRebuilding(false);

                    Turn turn = game.getTurn();

                    Player player = turn.getPlayer();

                    if(turn.getPhase().equals(Phase.BUY)) {
                        player.onBuyPhase();
                    } else if(turn.getPhase().equals(Phase.ACTION)) {
                        player.onActionPhase();
                    } else if(turn.getPhase().equals(Phase.WAITING_FOR_CHOICE)) {
                        game.getChoices().forEach((choice) -> choice.getTarget().onChoice());
                    }

                    return game;
                });
    }

    public Optional<Match> getMatch(Long matchId) {
        return matchRepository
                .findById(matchId)
                .map(MatchEntity::toMatch);
    }

    private Game applyCommands(Game game) {
        List<Command> commands;
        try {
            commands = commandService
                    .findCommandsForGame(game);

            if(commands.size() == 0) {
                game.setRebuilding(false);
                game.nextPlayer();
                game.resetPastTurns();
                game.getLogs().add("Start of Game");
            } else {
                game.ensureTurnerator();
                Player nextPlayer = game.getTurnerator().next();
                game.setTurn(nextPlayer.getCurrentTurn());
                game.resetPastTurns();
                game.getLogs().add("Start of Game");

                commands.forEach(command ->
                        applyCommand(game, command));
            }

        } catch (Exception e) {
            StringWriter outError = new StringWriter();
            e.printStackTrace(new PrintWriter(outError));
            logger.error(outError.toString());
        }

        return game;
    }

    public Game applyCommand(Game game, Command command) {

        try {
            game = commandService.applyCommand(game, command);

            if (game.isGameOver() && !game.getReadOnly()) {
                endGame(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;
    }

    public Match createMatch(Match match) {
        if (match.getMatchState().equals(MatchState.IN_PROGRESS)) {
            match.shuffleTurnOrder();
        }

        MatchEntity entity = matchRepository.save(MatchEntity.ofMatch(match));

        return entity.toMatch();
    }

    public void addPlayerAccount(Match match, Account account) {
        logger.info("Player {} joined game {}", account.getId(), match.getId());
        MatchParticipant matchParticipant = new MatchParticipant(account);
        match.addParticipant(matchParticipant);
        match.shuffleTurnOrder();

        if (match.isReady()) {
            match.setMatchState(MatchState.IN_PROGRESS);
        }

        matchRepository.save(MatchEntity.ofMatch(match));
    }

    private void endGame(Game game) {
        if (game.isGameOver()) {

            Optional<MatchEntity> optionalMatchEntity = matchRepository.findById(game.getId());

            Long winningPlayerId = game.getWinningPlayer().get().getId();
            Collection<Player> players = game.getPlayers().values();
            optionalMatchEntity
                    .filter((matchEntity) -> !matchEntity.getState().equals(MatchState.FINISHED))
                    .map((matchEntity) -> {
                        AccountEntity winner = matchEntity.findPlayerById(winningPlayerId);
                        matchEntity.setState(MatchState.FINISHED);
                        matchEntity.setWinner(winner);
                        Set<PlayerScoreEntity> scores = new HashSet<>();

                        for (Player player : players) {
                            PlayerScoreEntity playerScoreEntity = new PlayerScoreEntity();
                            playerScoreEntity.setAccount(matchEntity.findPlayerById(player.getId()));
                            playerScoreEntity.setScore(player.getVictoryPoints());

                            scores.add(playerScoreEntity);
                        }

                        matchEntity.setFinishedAt(LocalDateTime.now());

                        matchEntity.setScores(scores);
                        matchEntity = matchRepository.save(matchEntity);



                        eloService.updateEloForAccounts(
                                players.parallelStream()
                                        .map((Player::getAccount))
                                        .collect(Collectors.toList()),
                                winningPlayerId
                        );

                        return matchEntity.toMatch();
                    });
        }
    }

    @Inject
    public void setGameFactory(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }
}
