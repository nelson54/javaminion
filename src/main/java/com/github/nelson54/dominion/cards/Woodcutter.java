package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;


public class Woodcutter extends ActionCard {

    public Woodcutter() {
        super();
        byte moneyCost = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Woodcutter");
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        turn.setBuyPool(turn.getBuyPool() + 1);
        turn.setMoneyPool(turn.getMoneyPool() + 2);
    }
}
