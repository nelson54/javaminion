package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.Kingdom;
import com.github.nelson54.dominion.cards.KingdomFactory;
import com.github.nelson54.dominion.cards.Kingdom;
import com.github.nelson54.dominion.cards.KingdomFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by dnelson on 2/26/2015.
 */
public class GameFactory {

    KingdomFactory kingdomFactory;

    public Game createGame(int players) throws InstantiationException, IllegalAccessException {
        Game game = new Game();

        game.setPhase(Phase.BUY);
        game.setKingdom(kingdomFactory.getKingdom());
        game.setPlayers(new HashMap<>());
        game.setTurnOrder(new HashSet<>());

        addPlayers(players, game);

        game.nextPlayer();
        game.endPhase();

        return game;
    }

    void addPlayers(int players, Game game){
        for(; players > 0; players--){
            addPlayer(game, game.getPlayers(), game.getKingdom());
        }
    }

    void addPlayer(Game game, Map<String, Player> players, Kingdom kingdom){
        Player player = new Player();
        addStartingCardsToPlayer(player, kingdom);

        players.put(player.getId().toString(), player);
        game.getTurnOrder().add(player);

        player.resetForTurn();
    }

    void addStartingCardsToPlayer(Player player, Kingdom kingdom){

        kingdom.giveCardToPlayer("Estate", player);
        kingdom.giveCardToPlayer("Estate", player);
        kingdom.giveCardToPlayer("Estate", player);
        kingdom.giveCardToPlayer("Copper", player);
        kingdom.giveCardToPlayer("Copper", player);
        kingdom.giveCardToPlayer("Copper", player);
        kingdom.giveCardToPlayer("Copper", player);
        kingdom.giveCardToPlayer("Copper", player);
        kingdom.giveCardToPlayer("Copper", player);
        kingdom.giveCardToPlayer("Copper", player);

    }

    public KingdomFactory getKingdomFactory() {
        return kingdomFactory;
    }

    public void setKingdomFactory(KingdomFactory kingdomFactory) {
        this.kingdomFactory = kingdomFactory;
    }
}
