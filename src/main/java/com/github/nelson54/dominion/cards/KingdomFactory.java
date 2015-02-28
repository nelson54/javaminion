package com.github.nelson54.dominion.cards;

import com.google.common.collect.ArrayListMultimap;

/**
 * Created by dnelson on 2/26/2015.
 */
public class KingdomFactory {


    public Kingdom getKingdom() throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.cardMarket = ArrayListMultimap.create();

        addTreasureCards(kingdom);
        addVictoryCards(kingdom);

        return kingdom;
    }

    void addTreasureCards(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(60, Copper.class, kingdom);
        addXCardsOfType(60, Silver.class, kingdom);
        addXCardsOfType(60, Gold.class, kingdom);
    }

    void addVictoryCards(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(30, Estate.class, kingdom);
        addXCardsOfType(20, Duchy.class, kingdom);
        addXCardsOfType(10, Province.class, kingdom);
    }

    void addXCardsOfType(int x, Class<? extends Card> clazz, Kingdom kingdom) throws IllegalAccessException, InstantiationException {
        for(; x > 0; x--) {
            Card card = clazz.newInstance();
            kingdom.cardMarket.put(card.getName(), card);
        }
    }
}
