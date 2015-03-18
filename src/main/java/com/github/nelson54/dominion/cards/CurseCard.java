package com.github.nelson54.dominion.cards;


public class CurseCard extends Card {

    byte moneyCost = 0;
    private byte victoryPoints = -1;

    public CurseCard() {
        super();
        cardTypes.add(CardType.CURSE);

        byte moneyCost = 0;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        kingdomSortOrder = 100;

        setName("Curse");
    }

    public byte getVictoryPoints() {
        return victoryPoints;
    }

    void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
