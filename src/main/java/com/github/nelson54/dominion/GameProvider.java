package com.github.nelson54.dominion;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.google.common.collect.Multimap;
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

    private Multimap<String, String> tokenToIds;
    private Map<String, Game> gamesById;
    private Set<String> games;

    public GameProvider() {
        gamesById = new HashMap<>();
        games = new LinkedHashSet<>();
    }

    public Game getGameByUuid(String uuid) {
        return gamesById.get(uuid);
    }

    public Game createGameBySet(String cardSet) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        Game game = gameFactory.createGame(rc.getCards(), 2);
        games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);
        return game;
    }

    public Game createAiGameBySet(String cardSet) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        Game game = gameFactory.createAiGame(rc.getCards(), 2);
        games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);
        return game;
    }

    public Set<String> listGames() {
        return games;
    }
}
