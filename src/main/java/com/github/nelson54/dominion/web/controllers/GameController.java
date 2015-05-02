package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.cards.RecommendedCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/dominion")
public class GameController {

    @Autowired
    GameProvider gameProvider;

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    RecommendedCards[] getRecomendedCards(){
        return RecommendedCards.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    Set<String> getGames() throws InstantiationException, IllegalAccessException {
        return gameProvider.listGames();
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(
            @PathVariable("gameId")
            String id
    ) {
        return gameProvider.getGameByUuid(id);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.OPTIONS})
    Game createGame(
            @RequestBody com.github.nelson54.dominion.web.gamebuilder.Game game
    ) throws InstantiationException, IllegalAccessException {
        gameProvider.getMatches().add(game);
        return gameProvider.createAiGameBySet(game.getCardSet(), game.numberOfAiPlayers(), game.numberOfHumanPlayers());
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @PathVariable("gameId")
            String id
    ) {
        Game game = gameProvider.getGameByUuid(id);
        game.getTurn().endPhase();

        return game;
    }

    @RequestMapping("/matches")
    Page<com.github.nelson54.dominion.web.gamebuilder.Game> matches(){
        return new PageImpl<>(gameProvider.getMatches());
    }
}