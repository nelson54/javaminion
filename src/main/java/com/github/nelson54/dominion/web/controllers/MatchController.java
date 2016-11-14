package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.exceptions.InvalidCardSetName;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.match.MatchProvider;
import com.github.nelson54.dominion.web.gamebuilder.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static sun.audio.AudioPlayer.player;

@RestController
@RequestMapping("/dominion")
public class MatchController {

    @Autowired
    UsersProvider usersProvider;

    @Autowired
    GameProvider gameProvider;

    @Autowired
    MatchProvider matchProvider;

    @Autowired
    GameFactory gameFactory;

    public MatchController() {

    }

    @RequestMapping(value="/matches", method= RequestMethod.GET)
    Page<Match> matches() {
        User user = getUser();
        return new PageImpl<>(matchProvider.getJoinableMatchesForUser(user));
    }

    @RequestMapping(value = "/matches", method = RequestMethod.POST)
    void createMatch(
            @RequestParam
            byte numberOfHumanPlayers,
            @RequestParam
            byte numberOfAiPlayers,
            @RequestParam
            String cards
    ) throws InstantiationException, IllegalAccessException {

        byte totalPlayers = (byte)(numberOfAiPlayers + numberOfHumanPlayers);

        GameCardSet gameCardSet;
        GameCards gameCards = GameCards.ofName(cards);
        if(gameCards != null) {
            gameCardSet = gameCards.getGameCardSet();
        } else {
            throw new InvalidCardSetName();
        }

        Match match = new Match(totalPlayers, gameCardSet);

        User user = getUser();

        match.addParticipant(new MatchParticipant(user));
        match.addAiParticipants(numberOfAiPlayers);

        addMatch(match);
    }

    @RequestMapping(value="/matches", method=RequestMethod.PATCH)
    private void join(
            @RequestParam
            String matchId
    ) throws InstantiationException, IllegalAccessException {
        Match match = matchProvider.getMatchById(matchId);
        User user = getUser();
        match.addParticipant(new MatchParticipant(user));
        createGameIfReady(match);
    }

    private void addMatch (Match match) throws IllegalAccessException, InstantiationException {
        if(match.isReady()) {
            createGame(match);
        } else {
            matchProvider.addMatch(match);
        }
    }

    private void createGameIfReady(Match match) throws IllegalAccessException, InstantiationException {
        if(match.isReady()) {
            createGame(match);
        }
    }

    private void createGame(Match match) throws IllegalAccessException, InstantiationException {
        Game game = gameFactory.createGame(match);
        gameProvider.addGame(game);
        matchProvider.remove(match);
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return usersProvider.getUserById(authentication.getName());
    }
}
