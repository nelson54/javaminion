package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;


public class Festival extends ActionCard {

    public Festival() {
        super();
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Festival");
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        turn.setBuyPool(turn.getBuyPool()+1);
        turn.setActionPool(turn.getActionPool() + 2);
        turn.setMoneyPool(turn.getMoneyPool() + 2);
    }
}
