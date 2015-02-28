package com.github.nelson54.dominion.cards;

import com.google.common.collect.ArrayListMultimap;

/**
 * Created by dnelson on 2/26/2015.
 */
public class KingdomFactory {

    Kingdom kingdom;

    public Kingdom getKingdom() throws IllegalAccessException, InstantiationException {
        if(kingdom != null){
            return kingdom;
        }

        kingdom = new Kingdom();
        kingdom.cardMarket = ArrayListMultimap.create();

        addTreasureCards();
        addVictoryCards();

        return kingdom;
    }

    void addTreasureCards() throws InstantiationException, IllegalAccessException {
        addXCardsOfType(60, Copper.class);
        addXCardsOfType(60, Silver.class);
        addXCardsOfType(60, Gold.class);
    }

    void addVictoryCards() throws InstantiationException, IllegalAccessException {
        addXCardsOfType(30, Estate.class);
        addXCardsOfType(20, Duchy.class);
        addXCardsOfType(10, Province.class);
    }

    void addXCardsOfType(int x, Class<? extends Card> clazz) throws IllegalAccessException, InstantiationException {
        for(; x > 0; x--) {
            Card card = clazz.newInstance();
            kingdom.cardMarket.put(card.getName(), card);
        }
    }
}
