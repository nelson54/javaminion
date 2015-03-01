package com.github.nelson54.dominion.cards;

/**
 * Created by dnelson on 2/26/2015.
 */
class Province extends VictoryCard {
    public Province() {
        super();

        byte moneyCost = 8;
        byte victoryPoints = 6;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);
        setName("Province");
    }
}
