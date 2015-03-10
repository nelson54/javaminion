package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dnelson on 3/1/2015.
 */
public class Militia extends ActionAttackCard {

    public Militia() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Militia");
    }

    @Override
    public void attack(Player player, Game game) {
        throw new NotImplementedException();
    }

    @Override
    public void bonus(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        turn.setMoneyPool(turn.getMoneyPool() + 2);
    }
}
