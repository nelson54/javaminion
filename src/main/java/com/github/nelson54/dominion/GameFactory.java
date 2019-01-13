package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;



public class GameFactory {

    private KingdomFactory kingdomFactory;

    public Game createGame(Match match) {
        Game game = new Game(match.getSeed());
        try {
            game.setPlayers(new HashMap<>());
            game.setTurnOrder(new LinkedHashSet<>());


                game.setKingdom(kingdomFactory.getKingdomFromCards(match.getCards().getCardClasses(), match.getPlayerCount()));


            addPlayers(match.getParticipants(), game);
            game.nextPlayer();
            Iterator<Player> turnerator =  game.getTurnOrder().iterator();

            byte i = 0;

            while(turnerator.hasNext()) {
                turnerator.next().setOrder(i++);
            }

        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    private void addPlayers(Collection<MatchParticipant> players, Game game) {

        for(MatchParticipant player : players){
            Player p;

            if(player.isAi()){
                p = createAiPlayer(game, player.getAccount(), game.getKingdom());
            } else {
                p = createHumanPlayer(game, player.getAccount(), game.getKingdom());
            }

            game.getPlayers().put(p.getId(), p);
        }
    }

    private Player createHumanPlayer(Game game, Account account, Kingdom kingdom) {


        Player player = new Player(account);
        return getPlayer(game, player);
    }

    private Player getPlayer(Game game, Player player) {
        player.setGame(game);

        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    private Player createAiPlayer(Game game, Account account, Kingdom kingdom) {

        return createAiPlayer(game, account, AiStrategies.random(), kingdom);
    }

    private Player createAiPlayer(Game game, Account account, AiStrategy aiStrategy, Kingdom kingdom) {

        AiPlayer player = new AiPlayer(account);
        player.setAiStrategy(aiStrategy);
        return getPlayer(game, player);
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
