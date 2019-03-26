package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.sets.base.*;
import com.github.nelson54.dominion.cards.base.*;
import com.github.nelson54.dominion.cards.types.Card;
import com.google.common.collect.ArrayListMultimap;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

@Service
public class KingdomFactory {

    private static Kingdom createKingdom() {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());
        return kingdom;
    }

    Kingdom getKingdomFromCards(Random random, Set<Class<? extends Card>> cards, int players)
            throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = createKingdom();

        addTreasureCards(random, kingdom);
        addVictoryCards(random, kingdom, players);

        cards.forEach(clazz -> {
            try {
                addXCardsOfType(random, 10, clazz, kingdom);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });

        return kingdom;
    }

    private void addTreasureCards(Random random, Kingdom kingdom)
            throws InstantiationException, IllegalAccessException {

        addXCardsOfType(random, 60, Copper.class, kingdom);
        addXCardsOfType(random, 60, Silver.class, kingdom);
        addXCardsOfType(random, 60, Gold.class, kingdom);
    }

    private void addVictoryCards(Random random, Kingdom kingdom, int players)
            throws InstantiationException, IllegalAccessException {

        int provinces = (players == 2) ? 8 : 12;

        addXCardsOfType(random, 30, Estate.class, kingdom);
        addXCardsOfType(random, 20, Duchy.class, kingdom);
        addXCardsOfType(random, provinces, Province.class, kingdom);
        addXCardsOfType(random, 20, CurseCard.class, kingdom);

    }

    private void addXCardsOfType(
            Random random,
            int x,
            Class<? extends Card> clazz,
            Kingdom kingdom) throws IllegalAccessException, InstantiationException {
        try {
            for (; x > 0; x--) {
                Card card = clazz.getDeclaredConstructor(Long.class).newInstance(random.nextLong());

                kingdom.getCardMarket().put(card.getName(), card);
                kingdom.getAllCards().put(card.getId(), card);
            }
        } catch (InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
