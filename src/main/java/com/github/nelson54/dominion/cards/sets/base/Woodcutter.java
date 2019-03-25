package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.Cost;


public class Woodcutter extends ActionCard {

    public Woodcutter(Long id) {
        super(id);
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
