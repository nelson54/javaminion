package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Player;

public class Gardens extends VictoryCard {

    public Gardens() {
        super();

        byte moneyCost = 5;
        byte victoryPoints = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setName("Gardens");
    }

    @Override
    public byte getVictoryPoints() {
        Player owner = getOwner();
        return (byte) (Math.floor(owner.getAllCards().size()) / 10 * 3);
    }
}
