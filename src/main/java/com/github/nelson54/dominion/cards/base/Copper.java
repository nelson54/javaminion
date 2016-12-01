package com.github.nelson54.dominion.cards.base;


import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.TreasureCard;

public class Copper extends TreasureCard {

    public Copper(String id, Player player) {
        this();
        super.setId(id);
        super.setOwner(player);
    }

    public Copper() {
        super();
        byte moneyCost = 0;
        byte moneyValue = 1;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);
        setKingdomSortOrder(10);
        setName("Copper");
    }
}
