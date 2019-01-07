package com.github.nelson54.dominion;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class GameProvider {

    @Autowired
    GameFactory gameFactory;

    private Map<Long, Game> gamesById;
    private Multimap<String, Game> gamesByPlayerId;
    //private Set<String> games;


    public GameProvider() {
        gamesById = new HashMap<>();
        gamesByPlayerId = ArrayListMultimap.create();
    }

    public void addGame(Game game) {
        game.getPlayers().forEach((id, player) -> gamesByPlayerId.put(id, game));
        gamesById.put(game.getId(), game);
    }

    public Game getGameByUuid(String uuid) {
        return gamesById.get(uuid);
    }

    public Collection<Game> getMatches(String id) {
        return gamesByPlayerId.get(id);
    }

    public Collection<Game> all() {
        return gamesById.values();
    }
}
