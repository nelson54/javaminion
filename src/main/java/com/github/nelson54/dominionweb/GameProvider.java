package com.github.nelson54.dominionweb;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GameProvider {

    @Autowired
    GameFactory gameFactory;

    Map<String, Game> games;

    public GameProvider(){
        games = new HashMap<>();
    }

    public Game getGameByUuid(String uuid){
        return games.get(uuid);
    }

    public Game createGame() throws IllegalAccessException, InstantiationException {
        Game game = gameFactory.createGame(2);
        games.put(game.getId().toString(), game);
        return game;
    }

    public Set<String> listGames(){
        return games.keySet();
    }
}
