package com.github.nelson54.dominion.cards.base;


import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.TreasureCard;

public class Gold extends TreasureCard {

    public Gold() {
        super();
        byte moneyCost = 6;
        byte moneyValue = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setMoneyValue(moneyValue);
        setKingdomSortOrder(12);

        setName("Gold");
    }

    public Gold(String id, Player player) {
        this();
        super.setId(id);
        super.setOwner(player);
    }
}
