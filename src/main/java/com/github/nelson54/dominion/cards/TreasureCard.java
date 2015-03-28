package com.github.nelson54.dominion.cards;

public abstract class TreasureCard extends Card {

    byte moneyCost;
    private byte moneyValue;

    public TreasureCard() {
        super();
        isKingdom = false;
        cardTypes.add(CardType.TREASURE);
    }

    public byte getMoneyValue() {
        return moneyValue;
    }

    void setMoneyValue(byte moneyValue) {
        this.moneyValue = moneyValue;
    }
}
