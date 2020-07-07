package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.Cost;

import java.util.Map;


public class CouncilRoom extends ActionCard {

    public CouncilRoom(Long id) {
        super(id);
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Council Room");
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        player.drawXCards(4);
        turn.setBuyPool(turn.getBuyPool() + 1);
        Map<String, Player> players = game.getPlayers();

        players
                .values()
                .stream()
                .filter(p -> !p.getId().equals(player.getId()))
                .forEach(p -> p.drawXCards(1));
    }
}
