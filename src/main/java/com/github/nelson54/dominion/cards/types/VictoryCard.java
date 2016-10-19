package com.github.nelson54.dominion.cards.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.cards.CardType;


public abstract class VictoryCard extends Card {

    byte moneyCost;
    private byte victoryPoints;

    public VictoryCard() {
        super();
        isKingdom = false;
        cardTypes.add(CardType.VICTORY);
    }

    @JsonIgnore
    public byte getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
