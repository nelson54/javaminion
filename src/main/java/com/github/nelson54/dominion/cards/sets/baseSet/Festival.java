package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.Cost;


public class Festival extends ActionCard {

    public Festival(Long id) {
        super(id);
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Festival");
    }

    public Festival(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        turn.setBuyPool(turn.getBuyPool() + 1);
        turn.setActionPool(turn.getActionPool() + 2);
        turn.setMoneyPool(turn.getMoneyPool() + 2);
    }
}
