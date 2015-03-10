package com.github.nelson54.dominion.cards;


public class Silver extends TreasureCard {

    public Silver() {
        super();
        byte moneyCost = 3;
        byte moneyValue = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);

        setName("Silver");
    }
}
