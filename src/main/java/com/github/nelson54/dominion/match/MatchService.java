package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.game.commands.CommandService;
import com.github.nelson54.dominion.game.PhaseAdvisor;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.GameFactory;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.user.account.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private final MatchRepository matchRepository;
    private final CommandService commandService;
    private final EloService eloService;
    private final PhaseAdvisor phaseAdvisor;

    private GameFactory gameFactory;

    public MatchService(
            MatchRepository matchRepository,
            CommandService commandService,
            EloService eloService,
            PhaseAdvisor phaseAdvisor) {

        this.commandService = commandService;
        this.matchRepository = matchRepository;
        this.eloService = eloService;
        this.phaseAdvisor = phaseAdvisor;
    }

    public Page<Match> findByStateIn(List<MatchState> states, PageRequest page) {
        Page<MatchEntity> matchEntityPage = matchRepository.findByStateIn(states, page);

        List<Match> matches = StreamSupport
                .stream(matchEntityPage.spliterator(), false)//matchRepository.findByStateIn(states).spliterator(), false)
                .map(MatchEntity::toMatch)
                .collect(Collectors.toList());


        return new PageImpl<>(
                matches,
                matchEntityPage.getPageable(),
                matchEntityPage.getTotalElements());
    }

    public List<Match> all() {
        return StreamSupport
                .stream(matchRepository.findAll().spliterator(), false)
                .map(MatchEntity::toMatch)
                .collect(Collectors.toList());
    }

    public Optional<Game> getGame(String matchId) {
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

                    phaseAdvisor.advise(game);

                    return game;
                });
    }

    public Optional<Game> getGameUpToCommand(String matchId, String commandId) {
        return matchRepository
                .findById(matchId)
                .map(MatchEntity::toMatch)
                .map(gameFactory::createGame)
                .map(game -> {
                    game.setRebuilding(true);
                    return game;
                })
                .map(game -> applyCommandsUntil(game, commandId))
                .map(game -> {
                    game.setRebuilding(false);

                    phaseAdvisor.advise(game);

                    return game;
                });
    }

    public Optional<Match> getMatch(String matchId) {
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
                prepareForCommands(game);

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

    private Game applyCommandsUntil(Game game, String commandId) {
        List<Command> commands;
        try {
            commands = commandService
                    .findCommandsForGame(game);

            boolean hasReachedCommand = false;

            if(commands.size() == 0) {
                game.setRebuilding(false);
                game.nextPlayer();
                game.resetPastTurns();
                game.getLogs().add("Start of Game");
            } else {
                prepareForCommands(game);

                Iterator<Command> commandIterator = commands.iterator();

                while(!hasReachedCommand && commandIterator.hasNext()) {
                    Command command = commandIterator.next();
                    hasReachedCommand = command.getId().equals(commandId);
                    applyCommand(game, command);
                }
            }

        } catch (Exception e) {
            StringWriter outError = new StringWriter();
            e.printStackTrace(new PrintWriter(outError));
            logger.error(outError.toString());
        }

        return game;
    }

    private void prepareForCommands(Game game) {
        game.ensureTurnerator();
        Player nextPlayer = game.getTurnerator().next();
        game.setTurn(nextPlayer.getCurrentTurn());
        game.resetPastTurns();
        game.getLogs().add("Start of Game");
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
        MatchEntity entity = MatchEntity.ofMatch(match);

        return matchRepository.save(entity).toMatch();
    }

    public void addPlayerAccount(Match match, Account account) {
        logger.info("Player {} joined game {}", account.getId(), match.getId());
        MatchParticipant matchParticipant = new MatchParticipant(account);
        match.addParticipant(matchParticipant);
        match.shuffleTurnOrder();

        if (match.isReady()) {
            match.setMatchState(MatchState.IN_PROGRESS);
        }

        MatchEntity matchEntity = MatchEntity.ofMatch(match);
        matchEntity.setPersisted(true);
        matchRepository.save(matchEntity);
    }

    private void endGame(Game game) {
        if (game.isGameOver()) {

            Optional<MatchEntity> optionalMatchEntity = matchRepository.findById(game.getId());

            String winningPlayerId = game.getWinningPlayer().get().getId();
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

                        matchEntity.finishedAt = LocalDateTime.now();

                        matchEntity.setScores(scores);
                        matchEntity.setPersisted(true);
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

    @Autowired
    public void setGameFactory(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }
}
