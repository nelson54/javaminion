package com.github.nelson54.dominion.cards;


public class Copper extends TreasureCard {

    public Copper() {
        super();
        byte moneyCost = 0;
        byte moneyValue = 1;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);

        setName("Copper");
    }
}
