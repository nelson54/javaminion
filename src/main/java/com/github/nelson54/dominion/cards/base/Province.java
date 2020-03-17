package com.github.nelson54.dominion.cards.base;

import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.VictoryCard;

public class Province extends VictoryCard {
    public Province(Long id) {
        super(id);

        byte moneyCost = 8;
        byte victoryPoints = 6;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);
        setKingdomSortOrder(2);
        setName("Province");
    }
}
