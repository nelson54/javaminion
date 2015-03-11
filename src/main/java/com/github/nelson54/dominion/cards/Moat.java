package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.effects.Effect;

public class Moat extends ActionReactionCard {

    public Moat() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Moat");
    }

    @Override
    public void apply(Player player, Game game) {
        player.drawXCards(2);
    }

    @Override
    public void react(Effect effect) {

    }

    @Override
    public void onEnterHand() {

    }
}
