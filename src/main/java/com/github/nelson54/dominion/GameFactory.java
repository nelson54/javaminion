package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiPlayer;
import com.github.nelson54.dominion.ai.AiProviders;
import com.github.nelson54.dominion.cards.Card;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class GameFactory {

    private KingdomFactory kingdomFactory;

    public Game createGame(Class<? extends Card>[] cards, int players) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdomFromCards(cards));
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        addPlayers(players, game);
        game.nextPlayer();

        return game;
    }

    public Game createAiGame(Class<? extends Card>[] cards, int players) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdomFromCards(cards));
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        addSomeAiPlayers(players, game);
        game.nextPlayer();

        return game;
    }

    public Game createGameAllCards(int players) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdomAllCards());
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        addPlayers(players, game);
        game.nextPlayer();

        return game;
    }

    void addPlayers(int players, Game game) {
        for (; players > 0; players--) {
            Player player = createHumanPlayer(game, game.getPlayers(), game.getKingdom());

            game.getPlayers().put(player.getId().toString(), player);

        }
    }

    void addSomeAiPlayers(int players, Game game) {
        for (; players > 0; players--) {
            Player player;
            if(players == 1) {
                player = createHumanPlayer(game, game.getPlayers(), game.getKingdom());
            } else {
                player = createAiPlayer(game, game.getPlayers(), game.getKingdom());
            }

            game.getPlayers().put(player.getId().toString(), player);

        }
    }

    Player createHumanPlayer(Game game, Map<String, Player> players, Kingdom kingdom) {
        Player player = new Player();
        player.setGame(game);
        addStartingCardsToPlayer(player, kingdom, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    Player createAiPlayer(Game game, Map<String, Player> players, Kingdom kingdom) {
        AiPlayer player = new AiPlayer();
        player.setAiProvider(AiProviders.random());
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
