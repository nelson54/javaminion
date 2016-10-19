package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.match.Match;

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

    public Game createGame(Match match) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new LinkedHashSet<>());

        game.setKingdom(kingdomFactory.getKingdomFromCards(match.getCards().getCardClasses(), match.getPlayerCount()));

        addPlayers(match.getUsers(), game);
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

        User user1 = new User(playerModel1.getId(), "p1");
        User user2 = new User(playerModel2.getId(), "p2");

        Player p1 = createAiPlayer(game, user1, ai1, game.getKingdom());
        Player p2 = createAiPlayer(game, user2, ai2, game.getKingdom());

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

    @Deprecated
    void addPlayers(Set<com.github.nelson54.dominion.web.gamebuilder.Player> players, Game game) {

        for(com.github.nelson54.dominion.web.gamebuilder.Player player : players){
            Player p;
            User user = new User(player.getId(), player.getName());

            if(player.isAi()){
                p = createAiPlayer(game, user, game.getKingdom());
            } else {
                p = createHumanPlayer(game, player, game.getKingdom());
            }

            game.getPlayers().put(p.getId(), p);
        }
    }

    void addPlayers(Collection<User> players, Game game) {

        for(User player : players){
            Player p;
            User user = new User(player.getId(), player.getName());

            if(player.isAi()){
                p = createAiPlayer(game, user, game.getKingdom());
            } else {
                p = createHumanPlayer(game, player, game.getKingdom());
            }

            game.getPlayers().put(p.getId(), p);
        }
    }

    @Deprecated
    private Player createHumanPlayer(Game game, com.github.nelson54.dominion.web.gamebuilder.Player playerModel, Kingdom kingdom) {
        User user = new User(playerModel.getId(), playerModel.getName());

        Player player = new Player(user);
        player.setGame(game);

        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    private Player createHumanPlayer(Game game, User playerModel, Kingdom kingdom) {
        User user = new User(playerModel.getId(), playerModel.getName());

        Player player = new Player(user);
        player.setGame(game);

        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    private Player createAiPlayer(Game game, User player, Kingdom kingdom) {

        return createAiPlayer(game, player, AiStrategies.random(), kingdom);
    }

    private Player createAiPlayer(Game game, User user, AiStrategy aiStrategy, Kingdom kingdom) {

        AiPlayer player = new AiPlayer(user);

        player.setAiStrategy(aiStrategy);
        player.setGame(game);
        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    private void addStartingCardsToPlayer(Player player, Game game) {

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
