package com.github.nelson54.dominion.cards;

/**
 * Created by dnelson on 2/26/2015.
 */
class Silver extends TreasureCard {

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
