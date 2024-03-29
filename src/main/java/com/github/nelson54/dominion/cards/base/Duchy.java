package com.github.nelson54.dominion.cards.base;

import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.VictoryCard;

public class Duchy extends VictoryCard {

    public Duchy(Long id) {
        super(id);

        byte moneyCost = 5;
        byte victoryPoints = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setKingdomSortOrder(1);

        setName("Duchy");
    }
}
