package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.persistence.MatchRepository;
import com.github.nelson54.dominion.persistence.entities.match.MatchEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MatchService {

    private MatchRepository matchRepository;
    private GameFactory gameFactory;

    public MatchService(MatchRepository matchRepository, GameFactory gameFactory) {
        this.matchRepository = matchRepository;
        this.gameFactory = gameFactory;
    }

    public Collection<Game> all() {
        return StreamSupport
                .stream(matchRepository.findAll().spliterator(), false)
                .map(MatchEntity::toMatch)
                .map(match -> gameFactory.createGame(match))
                .collect(Collectors.toList());
    }

    public Optional<Game> getGame(Long matchId){

        return matchRepository
                .findById(matchId)
                .map(MatchEntity::toMatch)
                .map(match -> gameFactory.createGame(match));
    }

    public Match createMatch(Match match) {
        MatchEntity entity = matchRepository.save(MatchEntity.ofMatch(match));
        return entity.toMatch();
    }

    public void addPlayerAccount(Match match, Account account) {
        MatchParticipant matchParticipant = new MatchParticipant(account);
        match.addParticipant(matchParticipant);

    }

    public void addAiPlayerAccount(Match match) {
        MatchParticipant matchParticipant = MatchParticipant.createAi();
        match.addParticipant(matchParticipant);

    }

    public void prepareToPlay() {

    }

    public void completeGame(Match match) {

    }

    private Match save(Match match) {
        return match;
    }
}
