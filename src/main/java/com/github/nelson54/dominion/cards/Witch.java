package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

/**
 * Created by dnelson on 3/1/2015.
 */
public class Witch extends ActionAttackCard {

    public Witch() {
        super();
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
