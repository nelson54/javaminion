package com.github.nelson54.dominion.cards.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;


public abstract class VictoryCard extends Card {

    byte moneyCost;
    private byte victoryPoints;

    public VictoryCard(Long id) {
        super(id);
        isKingdom = false;
        cardTypes.add(CardType.VICTORY);
    }

    public VictoryCard(Long id, Player player) {
        super(id, player);
    }

    @JsonIgnore
    public byte getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
