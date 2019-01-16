package com.github.nelson54.dominion.cards.base;


import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.Cost;

public class CurseCard extends Card {

    public CurseCard(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }

    byte moneyCost = 0;
    private byte victoryPoints = -1;

    public CurseCard(Long id) {
        super(id);
        getCardTypes().add(CardType.CURSE);

        byte moneyCost = 0;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);
        setVictoryPoints(victoryPoints);

        setKingdomSortOrder(100);

        setName("Curse");
    }

    public byte getVictoryPoints() {
        return victoryPoints;
    }

    void setVictoryPoints(byte victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
