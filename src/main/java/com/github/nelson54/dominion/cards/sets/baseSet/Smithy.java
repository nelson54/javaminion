package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
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

    public Smithy(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public void apply(Player player, Game game) {
        player.drawXCards(3);
    }
}
