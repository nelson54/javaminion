package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import org.springframework.stereotype.Component;

@Component
public class MatchService {

    public Game getGame(Long matchId){
        return null;
    }

    public Match createMatch() {
        return null;
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
