package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;


public class Smithy extends ActionCard {

    public Smithy() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Smithy");
    }

    @Override
    public void apply(Player player, Game game) {
        player.drawXCards(3);
    }
}
