package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.RecommendedCards;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameProvider {

    @Autowired
    GameFactory gameFactory;

    private HashMap<String, com.github.nelson54.dominion.web.gamebuilder.Game> matching;
    private Map<String, Game> gamesById;
    private Multimap<String, Game> gamesByPlayerId;
    //private Set<String> games;


    public GameProvider() {
        matching = new HashMap<>();
        gamesById = new HashMap<>();
        gamesByPlayerId = ArrayListMultimap.create();
    }

    public Game getGameByUuid(String uuid) {
        return gamesById.get(uuid);
    }

    public Game createGameBySet(com.github.nelson54.dominion.web.gamebuilder.Game gameModel) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(gameModel.getCardSet());
        Game game = gameFactory.createGame(rc.getCards(), gameModel);
        updateGameLookups(game);
        return game;
    }

    public Game createAiGameBySet(String cardSet, com.github.nelson54.dominion.web.gamebuilder.Game gameModel) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        Game game = gameFactory.createAiGame(rc.getCards(), gameModel);
        //games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);

        return game;
    }

    public HashMap<String, com.github.nelson54.dominion.web.gamebuilder.Game> getMatching() {
        return matching;
    }

    public Collection<Game> getGamesForPlayer(String id) {
        return gamesByPlayerId.get(id);
    }

    private void updateGameLookups(Game game){
        gamesById.put(game.getId().toString(), game);

        game.getPlayers().values().forEach(p -> gamesByPlayerId.put(p.getId(), game));
    }
}
