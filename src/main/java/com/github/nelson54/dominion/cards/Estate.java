package com.github.nelson54.dominion.cards;

/**
 * Created by dnelson on 2/26/2015.
 */
public class Estate extends VictoryCard {

    public Estate() {
        super();

        byte moneyCost = 2;
        byte victoryPoints = 1;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setName("Estate");
    }
}
