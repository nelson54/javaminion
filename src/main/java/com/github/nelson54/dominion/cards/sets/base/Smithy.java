package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.Cost;


public class Smithy extends ActionCard {

    public Smithy(Long id) {
        super(id);
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
