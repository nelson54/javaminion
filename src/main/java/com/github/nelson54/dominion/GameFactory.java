package com.github.nelson54.dominion;

import com.github.nelson54.dominion.events.GameEventFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class GameFactory {

    private KingdomFactory kingdomFactory;

    public Game createGame(int players) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdom());
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        addPlayers(players, game);
        game.nextPlayer();

        GameEventFactory gameEventFactory = new GameEventFactory(game);
        game.setGameEventFactory(gameEventFactory);

        return game;
    }

    void addPlayers(int players, Game game) {
        for (; players > 0; players--) {
            Player player = createPlayer(game, game.getPlayers(), game.getKingdom());

            game.getPlayers().put(player.getId().toString(), player);

        }
    }

    Player createPlayer(Game game, Map<String, Player> players, Kingdom kingdom) {
        Player player = new Player();
        player.setGame(game);
        addStartingCardsToPlayer(player, kingdom, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    void addStartingCardsToPlayer(Player player, Kingdom kingdom, Game game) {

        game.giveCardToPlayer("Estate", player);
        game.giveCardToPlayer("Estate", player);
        game.giveCardToPlayer("Estate", player);
        game.giveCardToPlayer("Copper", player);
        game.giveCardToPlayer("Copper", player);
        game.giveCardToPlayer("Copper", player);
        game.giveCardToPlayer("Copper", player);
        game.giveCardToPlayer("Copper", player);
        game.giveCardToPlayer("Copper", player);
        game.giveCardToPlayer("Copper", player);

    }

    public KingdomFactory getKingdomFactory() {
        return kingdomFactory;
    }

    public void setKingdomFactory(KingdomFactory kingdomFactory) {
        this.kingdomFactory = kingdomFactory;
    }
}
