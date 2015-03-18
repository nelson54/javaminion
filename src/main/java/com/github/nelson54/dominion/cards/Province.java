package com.github.nelson54.dominion.cards;


public class Province extends VictoryCard {
    public Province() {
        super();

        byte moneyCost = 8;
        byte victoryPoints = 6;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);
        kingdomSortOrder = 2;
        setName("Province");
    }
}
