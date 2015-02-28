package com.github.nelson54.dominion.web;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class GameProvider {

    @Autowired
    GameFactory gameFactory;

    Map<String, Game> gamesById;
    Set<String> games;

    public GameProvider(){
        gamesById = new HashMap<>();
        games = new LinkedHashSet<>();
    }

    public Game getGameByUuid(String uuid){
        return gamesById.get(uuid);
    }

    public Game createGame() throws IllegalAccessException, InstantiationException {
        Game game = gameFactory.createGame(2);
        games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);
        return game;
    }

    public Set<String> listGames(){
        return games;
    }
}
