package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.VictoryCard;

public class Gardens extends VictoryCard {

    public Gardens() {
        super();

        byte moneyCost = 4;
        byte victoryPoints = 0;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setKingdom(true);
        setName("Gardens");
    }

    public Gardens(String id, Player player) {
        this();
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public byte getVictoryPoints() {
        Player owner = getOwner();
        return (byte) (Math.floor(owner.getAllCards().size()) / 10 * 1);
    }
}
