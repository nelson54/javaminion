package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.CardType;

public abstract class TreasureCard extends Card {

    private byte moneyCost;
    private byte moneyValue;

    public TreasureCard(Long id, Player player) {
        super(id, player);
    }

    public TreasureCard(Long id) {
        super(id);
        isKingdom = false;
        cardTypes.add(CardType.TREASURE);
    }

    public byte getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(byte moneyValue) {
        this.moneyValue = moneyValue;
    }

    public void setMoneyCost(byte moneyCost) {
        this.moneyCost = moneyCost;
    }

    public byte getMoneyCost() {
        return moneyCost;
    }
}
