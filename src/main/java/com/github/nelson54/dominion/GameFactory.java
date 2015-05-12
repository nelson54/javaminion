package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiPlayer;
import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.Card;

import java.util.*;



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

        com.github.nelson54.dominion.web.gamebuilder.Player playerModel1 = new com.github.nelson54.dominion.web.gamebuilder.Player(UUID.randomUUID().toString());
        playerModel1.setName(ai1.getClass().toString());

        com.github.nelson54.dominion.web.gamebuilder.Player playerModel2 = new com.github.nelson54.dominion.web.gamebuilder.Player(UUID.randomUUID().toString());
        playerModel2.setName(ai2.getClass().toString());

        Player p1 = createAiPlayer(game, playerModel1, ai1, game.getKingdom());
        Player p2 = createAiPlayer(game, playerModel2, ai2, game.getKingdom());

        p1.setName("p1");
        p2.setName("p2");

        game.getPlayers().put(p1.getId(), p1);
        game.getPlayers().put(p2.getId(), p2);

        game.nextPlayer();

        return game;
    }

    public Game createGameAllCards(com.github.nelson54.dominion.web.gamebuilder.Game g) throws InstantiationException, IllegalAccessException {
        Game game = new Game();
        game.setId(UUID.fromString(g.getId()));

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
                p = createAiPlayer(game, player, game.getKingdom());
            } else {
                p = createHumanPlayer(game, player, game.getKingdom());
            }

            game.getPlayers().put(p.getId(), p);
        }
    }

    Player createHumanPlayer(Game game, com.github.nelson54.dominion.web.gamebuilder.Player playerModel, Kingdom kingdom) {
        Player player = new Player();
        player.setId(playerModel.getId());
        player.setGame(game);
        player.setName(playerModel.getName());

        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    Player createAiPlayer(Game game, com.github.nelson54.dominion.web.gamebuilder.Player player, Kingdom kingdom) {

        return createAiPlayer(game, player, AiStrategies.random(), kingdom);
    }

    Player createAiPlayer(Game game, com.github.nelson54.dominion.web.gamebuilder.Player p, AiStrategy aiStrategy, Kingdom kingdom) {
        AiPlayer player = new AiPlayer();
        player.setId(p.getId());
        player.setName(p.getName());
        player.setAiStrategy(aiStrategy);
        player.setGame(game);
        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    void addStartingCardsToPlayer(Player player, Game game) {

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

    public void setKingdomFactory(KingdomFactory kingdomFactory) {
        this.kingdomFactory = kingdomFactory;
    }
}
