package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ActionAttackCard;
import com.github.nelson54.dominion.cards.Cost;


public class Witch extends ActionAttackCard {

    public Witch() {
        super();
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Witch");
    }

    public Witch(String id, Player player) {
        this();
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public void attack(Player player, Game game) {
        game.giveCardToPlayer("Curse", player);
    }

    @Override
    public void bonus(Player player, Game game) {
        player.drawXCards(2);
    }
}
