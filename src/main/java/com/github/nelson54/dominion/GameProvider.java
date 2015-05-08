package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.web.gamebuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameProvider {

    @Autowired
    GameFactory gameFactory;

    private HashMap<String, com.github.nelson54.dominion.web.gamebuilder.Game> matching;
    private Map<String, Game> gamesById;
    private Set<String> games;

    public GameProvider() {
        matching = new HashMap<>();
        gamesById = new HashMap<>();
        games = new LinkedHashSet<>();
    }

    public Game getGameByUuid(String uuid) {
        return gamesById.get(uuid);
    }

    public Game createGameBySet(String cardSet, Set<com.github.nelson54.dominion.web.gamebuilder.Player> players) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        Game game = gameFactory.createGame(rc.getCards(), players);
        games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);
        return game;
    }

    public Game createAiGameBySet(String cardSet, int ai, int humans) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        Game game = gameFactory.createAiGame(rc.getCards(), ai, humans);
        games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);
        return game;
    }

    public HashMap<String, com.github.nelson54.dominion.web.gamebuilder.Game> getMatching() {
        return matching;
    }

    public Set<Game> getGamesForPlayer() {
        return null;
    }

    public Set<String> listGames() {
        return games;
    }
}
