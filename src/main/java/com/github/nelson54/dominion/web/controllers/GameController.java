package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.web.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/dominion")
public class GameController {

    @Autowired
    GameProvider gameProvider;

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
    Game createGame() throws InstantiationException, IllegalAccessException {
        return gameProvider.createGame();
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    void endPhase(
            @PathVariable("gameId")
            String id
    ) {
        Game game = gameProvider.getGameByUuid(id);
        game.getTurn().endPhase();
    }
}