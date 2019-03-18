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
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MatchService {
    private static final Logger logger = Logger.getLogger(MatchService.class);
    private MatchRepository matchRepository;
    private GameFactory gameFactory;
    private CommandService commandService;

    public MatchService(MatchRepository matchRepository, GameFactory gameFactory, CommandService commandService) {
        this.commandService = commandService;
        this.matchRepository = matchRepository;
        this.gameFactory = gameFactory;
    }

    public List<Match> all() {
        return StreamSupport
                .stream(matchRepository.findAll().spliterator(), false)
                .map(MatchEntity::toMatch)
                .collect(Collectors.toList());
    }

    @Transactional
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

        if(game.getGameOver()) {
            Optional<MatchEntity> optionalMatchEntity = matchRepository.findById(game.getId());
            Long winningPlayerId = game.getWinningPlayer().getId();
            Collection<Player> players = game.getPlayers().values();
            optionalMatchEntity.ifPresent((matchEntity) -> {
                AccountEntity winner = matchEntity.findPlayerById(winningPlayerId);
                matchEntity.setState(MatchState.FINISHED);
                matchEntity.setWinner(winner);
                Set<PlayerScoreEntity> scores = new HashSet<>();

                players.forEach((player)->{
                    PlayerScoreEntity playerScoreEntity = new PlayerScoreEntity();
                    playerScoreEntity.setScore(player.getVictoryPoints());

                    scores.add(playerScoreEntity);
                });

                matchEntity.setScores(scores);
            });
        }

        return game;
    }

    public Match createMatch(Match match) {
        MatchEntity entity = matchRepository.save(MatchEntity.ofMatch(match));
        return entity.toMatch();
    }

    public void addPlayerAccount(Match match, Account account) {
        MatchParticipant matchParticipant = new MatchParticipant(account);
        match.addParticipant(matchParticipant);
        matchRepository.save(MatchEntity.ofMatch(match));
    }

    public void addAiPlayerAccount(Match match) {
        MatchParticipant matchParticipant = MatchParticipant.createAi();
        match.addParticipant(matchParticipant);

    }

    public Optional<Match> endGame(Game game) {
        if(game.getGameOver()) {

            return matchRepository.findById(game.getId())
                    .map(MatchEntity::toMatch)
                    .map(match -> {
                        if (match.getMatchState().equals(MatchState.FINISHED)) {
                            return null;
                        }

                        match.setMatchState(MatchState.FINISHED);

                        return MatchEntity.ofMatch(match);
                    })
                    .map((matchEntity) -> matchRepository.save(matchEntity))
                    .map(MatchEntity::toMatch);
        }

        return null;
    }

    public void prepareToPlay() {

    }

    public void completeGame(Match match) {

    }

    private Match save(Match match) {
        return match;
    }


}
