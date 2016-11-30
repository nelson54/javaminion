package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.Cost;

import java.util.Map;


public class CouncilRoom extends ActionCard {

    public CouncilRoom() {
        super();
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Council Room");
    }

    public CouncilRoom(String id, Player player) {
        this();
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        player.drawXCards(4);
        turn.setBuyPool(turn.getBuyPool() + 1);
        Map<String, Player> players = game.getPlayers();

        players
                .keySet()
                .stream()
                .map(players::get)
                .filter(p -> !p.getId().equals(player.getId()))
                .forEach(p -> p.drawXCards(1));
    }
}
