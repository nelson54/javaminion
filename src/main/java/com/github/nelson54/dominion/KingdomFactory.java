package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.sets.baseSet.*;
import com.github.nelson54.dominion.cards.base.*;
import com.github.nelson54.dominion.cards.types.Card;
import com.google.common.collect.ArrayListMultimap;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class KingdomFactory {

    public Kingdom getKingdom(Long seed) throws IllegalAccessException, InstantiationException {
        Random random = new Random(seed);
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(random, kingdom);
        addVictoryCards(random, kingdom, 4);
        villageSquare(random, kingdom);

        return kingdom;
    }

    @Deprecated
    Kingdom getKingdomFromCards(Random random, Class<? extends Card>[] cards, int players) throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(random, kingdom);
        addVictoryCards(random, kingdom, players);

        for(Class<? extends Card> clazz: cards){
            addXCardsOfType(random, 10, clazz, kingdom);
        }

        return kingdom;
    }

    Kingdom getKingdomFromCards(Random random, Set<Class<? extends Card>> cards, int players) throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(random, kingdom);
        addVictoryCards(random, kingdom, players);

        cards.forEach(clazz -> {
            try {
                addXCardsOfType(random, 10, clazz, kingdom);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });

        return kingdom;
    }

    Kingdom getKingdomAllCards(Random random) throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(random, kingdom);
        addVictoryCards(random, kingdom, 4);
        addKingdomCards(random, kingdom);

        return kingdom;
    }

    private void addTreasureCards(Random random, Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(random, 60, Copper.class, kingdom);
        addXCardsOfType(random, 60, Silver.class, kingdom);
        addXCardsOfType(random, 60, Gold.class, kingdom);
    }

    private void addVictoryCards(Random random, Kingdom kingdom, int players) throws InstantiationException, IllegalAccessException {
        int provinces = (players == 2) ? 8 : 12;

        addXCardsOfType(random, 30, Estate.class, kingdom);
        addXCardsOfType(random, 20, Duchy.class, kingdom);
        addXCardsOfType(random, provinces, Province.class, kingdom);
        addXCardsOfType(random, 20, CurseCard.class, kingdom);
    }

    private void firstGame(Random random, Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(random, 10, Cellar.class, kingdom);
        addXCardsOfType(random, 10, Market.class, kingdom);
        addXCardsOfType(random, 10, Militia.class, kingdom);
        addXCardsOfType(random, 10, Mine.class, kingdom);
        addXCardsOfType(random, 10, Moat.class, kingdom);
        addXCardsOfType(random, 10, Remodel.class, kingdom);
        addXCardsOfType(random, 10, Smithy.class, kingdom);
        addXCardsOfType(random, 10, Village.class, kingdom);
        addXCardsOfType(random, 10, Woodcutter.class, kingdom);
        addXCardsOfType(random, 10, Workshop.class, kingdom);
    }

    private void villageSquare(Random random, Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(random, 10, Bureaucrat.class, kingdom);
        addXCardsOfType(random, 10, Cellar.class, kingdom);
        addXCardsOfType(random, 10, Festival.class, kingdom);
        addXCardsOfType(random, 10, Library.class, kingdom);
        addXCardsOfType(random, 10, Market.class, kingdom);
        addXCardsOfType(random, 10, Remodel.class, kingdom);
        addXCardsOfType(random, 10, Smithy.class, kingdom);
        addXCardsOfType(random, 10, ThroneRoom.class, kingdom);
        addXCardsOfType(random, 10, Woodcutter.class, kingdom);
        addXCardsOfType(random, 10, Village.class, kingdom);
    }

    private void sizeDistortion(Random random, Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(random, 10, Cellar.class, kingdom);
        addXCardsOfType(random, 10, Chapel.class, kingdom);
        addXCardsOfType(random, 10, Feast.class, kingdom);
        addXCardsOfType(random, 10, Gardens.class, kingdom);
        addXCardsOfType(random, 10, Laboratory.class, kingdom);
        addXCardsOfType(random, 10, Thief.class, kingdom);
        addXCardsOfType(random, 10, Village.class, kingdom);
        addXCardsOfType(random, 10, Witch.class, kingdom);
        addXCardsOfType(random, 10, Woodcutter.class, kingdom);
        addXCardsOfType(random, 10, Workshop.class, kingdom);
    }

    private void bigMoney(Random random, Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(random, 10, Cellar.class, kingdom);
        addXCardsOfType(random, 10, Chapel.class, kingdom);
        addXCardsOfType(random, 10, Feast.class, kingdom);
        addXCardsOfType(random, 10, Gardens.class, kingdom);
        addXCardsOfType(random, 10, Laboratory.class, kingdom);
        addXCardsOfType(random, 10, Thief.class, kingdom);
        addXCardsOfType(random, 10, Village.class, kingdom);
        addXCardsOfType(random, 10, Witch.class, kingdom);
        addXCardsOfType(random, 10, Woodcutter.class, kingdom);
        addXCardsOfType(random, 10, Workshop.class, kingdom);
    }

    private void addKingdomCards(Random random, Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(random, 10, Smithy.class, kingdom);
        addXCardsOfType(random, 10, Village.class, kingdom);
        addXCardsOfType(random, 10, CouncilRoom.class, kingdom);
        addXCardsOfType(random, 10, Feast.class, kingdom);
        addXCardsOfType(random, 10, Festival.class, kingdom);
        addXCardsOfType(random, 10, Gardens.class, kingdom);
        addXCardsOfType(random, 10, Laboratory.class, kingdom);
        addXCardsOfType(random, 10, Bureaucrat.class, kingdom);
        addXCardsOfType(random, 10, Market.class, kingdom);
        addXCardsOfType(random, 10, Moneylender.class, kingdom);
        addXCardsOfType(random, 10, ThroneRoom.class, kingdom);
        addXCardsOfType(random, 10, Witch.class, kingdom);
        addXCardsOfType(random, 10, Woodcutter.class, kingdom);
        addXCardsOfType(random, 10, Remodel.class, kingdom);
        addXCardsOfType(random, 10, Mine.class, kingdom);
        addXCardsOfType(random, 10, Workshop.class, kingdom);
        addXCardsOfType(random, 10, Chapel.class, kingdom);
        addXCardsOfType(random, 10, Adventurer.class, kingdom);
        addXCardsOfType(random, 10, Chancellor.class, kingdom);
        addXCardsOfType(random, 10, Militia.class, kingdom);
        addXCardsOfType(random, 10, Cellar.class, kingdom);
        addXCardsOfType(random, 10, Moat.class, kingdom);
        addXCardsOfType(random, 10, Library.class, kingdom);
        addXCardsOfType(random, 10, Spy.class, kingdom);
        addXCardsOfType(random, 10, Thief.class, kingdom);
    }

    private void addXCardsOfType(Random random, int x, Class<? extends Card> clazz, Kingdom kingdom) throws IllegalAccessException, InstantiationException {
        try {
            for (; x > 0; x--) {
                Card card = clazz.getDeclaredConstructor(Long.class).newInstance(random.nextLong());

                kingdom.getCardMarket().put(card.getName(), card);
                kingdom.getAllCards().put(card.getId().toString(), card);
            }
        } catch (InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
