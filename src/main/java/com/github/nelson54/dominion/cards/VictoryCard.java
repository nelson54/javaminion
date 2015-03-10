package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class VictoryCard extends Card {

    byte moneyCost;
    private byte victoryPoints;

    VictoryCard() {
        super();
        isKingdom = false;
        cardTypes.add(CardType.VICTORY);
    }

    @JsonIgnore
    public byte getVictoryPoints() {
        return victoryPoints;
    }

    void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
