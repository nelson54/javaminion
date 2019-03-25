package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ActionAttackCard;
import com.github.nelson54.dominion.cards.Cost;


public class Witch extends ActionAttackCard {

    public Witch(Long id) {
        super(id);
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Witch");
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
