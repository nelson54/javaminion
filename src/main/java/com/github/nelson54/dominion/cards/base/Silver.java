package com.github.nelson54.dominion.cards.base;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.TreasureCard;

public class Silver extends TreasureCard {

    private byte moneyValue = 2;
    private byte moneyCost = 3;

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

    public Silver(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }
}
