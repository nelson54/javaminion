package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

/**
 * Created by dnelson on 2/26/2015.
 */
public abstract class VictoryCard extends Card {

    byte moneyCost;
    byte victoryPoints;

    public VictoryCard() {
        super();
        cardTypes.add(CardType.VICTORY);
    }

    byte getVictoryPoints(Player player, Game game){
        return victoryPoints;
    }

    public void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
