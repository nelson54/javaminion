package com.github.nelson54.dominion;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.ai.AiPlayer;
import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.Card;

import java.util.*;
import java.util.stream.Collectors;


public class GameFactory {

    private KingdomFactory kingdomFactory;

    public Game createGame(Class<? extends Card>[] cards, com.github.nelson54.dominion.web.gamebuilder.Game gameModel) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new LinkedHashSet<>());

        game.setKingdom(kingdomFactory.getKingdomFromCards(cards, gameModel.getPlayers().size()));

        addPlayers(gameModel.getPlayers(), game);
        game.nextPlayer();

        return game;
    }

    public Game createAiGame(Class<? extends Card>[] cards, com.github.nelson54.dominion.web.gamebuilder.Game gameModel) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        game.setKingdom(kingdomFactory.getKingdomFromCards(cards, gameModel.getPlayers().size()));

        addPlayers(gameModel.getPlayers(), game);


        if(gameModel.numberOfHumanPlayers() > 0) {
            game.nextPlayer();
        }

        return game;
    }

    public Game createTwoPlayerAiGame(AiStrategy ai1, AiStrategy ai2, Class<? extends Card>[] cards) throws InstantiationException, IllegalAccessException {

        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdomFromCards(cards, 2));
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new LinkedHashSet<>());

        Player p1 = createAiPlayer(game, game.getPlayers(), ai1, game.getKingdom(), ai1.getClass().toString());
        Player p2 = createAiPlayer(game, game.getPlayers(), ai2, game.getKingdom(), ai2.getClass().toString());

        p1.setName("p1");
        p2.setName("p2");

        game.getPlayers().put(p1.getId(), p1);
        game.getPlayers().put(p2.getId(), p2);

        game.nextPlayer();

        return game;
    }

    public Game createGameAllCards(com.github.nelson54.dominion.web.gamebuilder.Game g) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setKingdom(kingdomFactory.getKingdomAllCards());
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new LinkedHashSet<>());

        addPlayers(g.getPlayers(), game);
        game.nextPlayer();

        return game;
    }

    void addPlayers(Set<com.github.nelson54.dominion.web.gamebuilder.Player> players, Game game) {
        for(com.github.nelson54.dominion.web.gamebuilder.Player player : players){
            Player p;
            if(player.isAi()){
                p = createAiPlayer(game, game.getPlayers(), game.getKingdom(), "Human ");
            } else {
                p = createHumanPlayer(game, game.getPlayers(), game.getKingdom(), "Human ");
            }

            game.getPlayers().put(p.getId(), p);
        }
    }

    void addAiPlayers(int players, Game game) {
        Iterator<AiName> aiNames = AiName.random(players).iterator();

        for (; players > 0; players--) {
            Player player;
            player = createAiPlayer(game, game.getPlayers(), game.getKingdom(), aiNames.next().toString());
            game.getPlayers().put(player.getId().toString(), player);
        }
    }

    Player createHumanPlayer(Game game, Map<String, Player> players, Kingdom kingdom, String name) {
        Player player = new Player();
        player.setId(UUID.randomUUID().toString());
        player.setGame(game);
        player.setName(name);

        addStartingCardsToPlayer(player, kingdom, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    Player createAiPlayer(Game game, Map<String, Player> players, Kingdom kingdom, String name) {
        return createAiPlayer(game, players, AiStrategies.random(), kingdom, name);
    }

    Player createAiPlayer(Game game, Map<String, Player> players, AiStrategy aiStrategy, Kingdom kingdom, String name) {
        AiPlayer player = new AiPlayer();
        player.setId(UUID.randomUUID().toString());
        player.setName(name);
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
