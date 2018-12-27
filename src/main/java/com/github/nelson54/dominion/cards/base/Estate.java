package com.github.nelson54.dominion.cards.base;


import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.VictoryCard;

public class Estate extends VictoryCard {

    public Estate() {
        super();

        byte moneyCost = 2;
        byte victoryPoints = 1;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setKingdomSortOrder(0);

        setName("Estate");
    }

    public Estate(String id, Player player) {
        this();
        super.setId(id);
        super.setOwner(player);
    }
}