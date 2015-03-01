package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.*;
import com.google.common.collect.ArrayListMultimap;

/**
 * Created by dnelson on 2/26/2015.
 */
public class KingdomFactory {

    public com.github.nelson54.dominion.Kingdom getKingdom() throws IllegalAccessException, InstantiationException {

        com.github.nelson54.dominion.Kingdom kingdom = new com.github.nelson54.dominion.Kingdom();
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(kingdom);
        addVictoryCards(kingdom);
        addKingdomCards(kingdom);

        return kingdom;
    }

    void addTreasureCards(com.github.nelson54.dominion.Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(60, Copper.class, kingdom);
        addXCardsOfType(60, Silver.class, kingdom);
        addXCardsOfType(60, Gold.class, kingdom);
    }

    void addVictoryCards(com.github.nelson54.dominion.Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(30, Estate.class, kingdom);
        addXCardsOfType(20, Duchy.class, kingdom);
        addXCardsOfType(10, Province.class, kingdom);
    }

    void addKingdomCards(com.github.nelson54.dominion.Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(10, Smithy.class, kingdom);
        addXCardsOfType(10, Village.class, kingdom);
    }

    void addXCardsOfType(int x, Class<? extends Card> clazz, com.github.nelson54.dominion.Kingdom kingdom) throws IllegalAccessException, InstantiationException {
        for(; x > 0; x--) {
            Card card = clazz.newInstance();
            kingdom.getCardMarket().put(card.getName(), card);
        }
    }
}
