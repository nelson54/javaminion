package com.github.nelson54.dominion.cards;

class Duchy extends VictoryCard {

    public Duchy() {
        super();

        byte moneyCost = 5;
        byte victoryPoints = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setName("Duchy");
    }
}
