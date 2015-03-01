package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;

/**
 * Created by dnelson on 2/28/2015.
 */
public class Village extends ActionCard {

    public Village() {
        super();
        byte moneyCost = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Village");
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        player.drawXCards(1);
        turn.setActionPool(turn.getActionPool() + 2);
    }
}
