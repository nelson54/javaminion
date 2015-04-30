package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.RecommendedCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameProvider {

    @Autowired
    GameFactory gameFactory;

    private List<com.github.nelson54.dominion.web.gamebuilder.Game> matching;
    private Map<String, Game> gamesById;
    private Set<String> games;

    public GameProvider() {
        matching = new ArrayList<>();
        gamesById = new HashMap<>();
        games = new LinkedHashSet<>();
    }

    public Game getGameByUuid(String uuid) {
        return gamesById.get(uuid);
    }

    public Game createGameBySet(String cardSet, int count) throws IllegalAccessException, InstantiationException {
        RecommendedCards rc = RecommendedCards.ofName(cardSet);

        Game game = gameFactory.createGame(rc.getCards(), count);
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

    public List<com.github.nelson54.dominion.web.gamebuilder.Game> getMatching() {
        return matching;
    }

    public void setMatching(List<com.github.nelson54.dominion.web.gamebuilder.Game> matching) {
        this.matching = matching;
    }

    public Set<String> listGames() {
        return games;
    }
}
