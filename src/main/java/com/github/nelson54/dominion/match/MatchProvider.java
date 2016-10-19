package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.web.gamebuilder.Game;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class MatchProvider {

    @Autowired
    GameFactory gameFactory;

    private HashMap<String, Game> matching;
    private Map<String, com.github.nelson54.dominion.Game> gamesById;
    private Multimap<String, com.github.nelson54.dominion.Game> gamesByPlayerId;
    //private Set<String> games;


    public MatchProvider() {
        matching = new HashMap<>();
        gamesById = new HashMap<>();
        gamesByPlayerId = ArrayListMultimap.create();
    }

    public com.github.nelson54.dominion.Game getGameByUuid(String uuid) {
        return gamesById.get(uuid);
    }

    public com.github.nelson54.dominion.Game createGameBySet(com.github.nelson54.dominion.web.gamebuilder.Game gameModel) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(gameModel.getCardSet());
        com.github.nelson54.dominion.Game game = gameFactory.createGame(rc.getCards(), gameModel);
        updateGameLookups(game);
        return game;
    }

    public com.github.nelson54.dominion.Game createAiGameBySet(String cardSet, com.github.nelson54.dominion.web.gamebuilder.Game gameModel) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        com.github.nelson54.dominion.Game game = gameFactory.createAiGame(rc.getCards(), gameModel);
        //games.add(game.getId().toString());
        gamesById.put(game.getId().toString(), game);

        return game;
    }

    public HashMap<String, com.github.nelson54.dominion.web.gamebuilder.Game> getMatching() {
        return matching;
    }

    public Collection<com.github.nelson54.dominion.Game> getMatches(String id) {
        return gamesByPlayerId.get(id);
    }

    private void updateGameLookups(com.github.nelson54.dominion.Game game){
        gamesById.put(game.getId().toString(), game);

        game.getPlayers().values().forEach(p -> gamesByPlayerId.put(p.getId(), game));
    }
}