package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.ai.AiPlayer;
import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.Card;

import java.util.*;


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

    public Game createTwoPlayerAiGame(AiStrategy ai1, AiStrategy ai2, Class<? extends Card>[] cards) throws InstantiationException, IllegalAccessException {

        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdomFromCards(cards));
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        Player p1 = createAiPlayer(game, game.getPlayers(), ai1, game.getKingdom());
        Player p2 = createAiPlayer(game, game.getPlayers(), ai2, game.getKingdom());

        p1.setName("p1");
        p2.setName("p2");

        game.getPlayers().put(p1.getId().toString(), p1);
        game.getPlayers().put(p2.getId().toString(), p2);

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
        Iterator<AiName> aiNames = AiName.random(players).iterator();

        for (; players > 0; players--) {
            Player player;
            if(players == 1) {
                player = createHumanPlayer(game, game.getPlayers(), game.getKingdom());
            } else {
                player = createAiPlayer(game, game.getPlayers(), game.getKingdom());
                player.setName(aiNames.next().toString());
            }

            game.getPlayers().put(player.getId().toString(), player);

        }
    }

    Player createHumanPlayer(Game game, Map<String, Player> players, Kingdom kingdom) {
        Player player = new Player();
        player.setGame(game);
        player.setName("You");
        addStartingCardsToPlayer(player, kingdom, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    Player createAiPlayer(Game game, Map<String, Player> players, Kingdom kingdom) {
        return createAiPlayer(game, players, AiStrategies.random(), kingdom);
    }

    Player createAiPlayer(Game game, Map<String, Player> players, AiStrategy aiStrategy, Kingdom kingdom) {
        AiPlayer player = new AiPlayer();
        player.setAiStrategy(aiStrategy);
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
