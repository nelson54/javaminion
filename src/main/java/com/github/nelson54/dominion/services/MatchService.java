package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.Player;
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


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private MatchRepository matchRepository;
    private GameFactory gameFactory;
    private CommandService commandService;
    private EloService eloService;

    public MatchService(
            MatchRepository matchRepository,
            GameFactory gameFactory,
            CommandService commandService,
            EloService eloService) {

        this.commandService = commandService;
        this.matchRepository = matchRepository;
        this.gameFactory = gameFactory;
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
                .map(this::applyCommands);
    }

    public Optional<Match> getMatch(Long matchId) {
        return matchRepository
                .findById(matchId)
                .map(MatchEntity::toMatch);
    }

    private Game applyCommands(Game game) {

        commandService
                .findCommandsForGame(game)
                .forEach(command -> applyCommand(game, command));

        return game;
    }

    public Game applyCommand(Game game, Command command) {

        game = commandService.applyCommand(game, command);

        if (game.isGameOver()) {
            endGame(game);
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
}
