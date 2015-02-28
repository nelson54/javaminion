package com.github.nelson54.dominionweb.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominionweb.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/dominion")
public class GameController {

    @Autowired
    GameProvider gameProvider;

    @RequestMapping(method=RequestMethod.GET)
    Set<String> getGames() throws InstantiationException, IllegalAccessException {
        return gameProvider.listGames();
    }

    @RequestMapping(value="/{gameId}", method={RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(
            @PathVariable("gameId")
            String id
    ) {
        return gameProvider.getGameByUuid(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    Game createGame() throws InstantiationException, IllegalAccessException {
        return gameProvider.createGame();
    }

    @RequestMapping(value = "/{gameId}/next-phase",method = RequestMethod.POST)
    void endPhase(
            @PathVariable("gameId")
            String id
    ){
        Game game = gameProvider.getGameByUuid(id);
        game.endPhase();
    }

}