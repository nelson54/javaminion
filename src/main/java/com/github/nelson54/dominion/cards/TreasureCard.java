package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

/**
 * Created by dnelson on 2/26/2015.
 */
public abstract class TreasureCard extends Card {

    byte moneyCost;
    private byte moneyValue;

    public TreasureCard() {
        super();
        isKingdom = false;
        cardTypes.add(CardType.TREASURE);
    }

    public byte getMoneyValue(Player player, Game game){
        return moneyValue;
    }

    void setMoneyValue(byte moneyValue){
        this.moneyValue = moneyValue;
    }
}
