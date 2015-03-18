package com.github.nelson54.dominion.cards;


public class Gold extends TreasureCard {

    public Gold() {
        super();
        byte moneyCost = 6;
        byte moneyValue = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);
        kingdomSortOrder = 12;

        setName("Gold");
    }
}
