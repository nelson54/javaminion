package com.github.nelson54.dominion.cards.base;

import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.TreasureCard;

public class Silver extends TreasureCard {

    public Silver(Long id) {
        super(id);
        byte moneyCost = 3;
        byte moneyValue = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);
        setKingdomSortOrder(11);
        setName("Silver");
    }
}
