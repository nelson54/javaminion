package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;

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

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        player.drawXCards(4);
        turn.setBuyPool(turn.getBuyPool()+1);
        Map<String, Player> players = game.getPlayers();

        game.getPlayers()
                .keySet()
                .stream()
                .map(players::get)
                .filter(p -> !p.getId().equals(player.getId()))
                .forEach(p -> p.drawXCards(1));
    }
}
