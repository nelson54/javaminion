package com.github.nelson54.dominion.cards.base;

import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.TreasureCard;

public class Gold extends TreasureCard {

    public Gold(Long id) {
        super(id);
        byte moneyCost = 6;
        byte moneyValue = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);
        setKingdomSortOrder(12);

        setName("Gold");
    }
}
