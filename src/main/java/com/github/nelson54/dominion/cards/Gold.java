package com.github.nelson54.dominion.cards;

/**
 * Created by dnelson on 2/26/2015.
 */
public class Gold extends TreasureCard {

    Gold(){
        super();
        byte moneyCost = 6;
        byte moneyValue = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);

        setName("Gold");
    }
}
