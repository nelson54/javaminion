package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by dnelson on 2/26/2015.
 */
public abstract class VictoryCard extends Card {

    byte moneyCost;
    private byte victoryPoints;

    public VictoryCard() {
        super();
        cardTypes.add(CardType.VICTORY);
    }

    @JsonIgnore
    public byte getVictoryPoints(){
        return victoryPoints;
    }

    void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
