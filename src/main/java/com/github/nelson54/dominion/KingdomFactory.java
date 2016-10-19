package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.sets.baseSet.*;
import com.github.nelson54.dominion.cards.base.*;
import com.github.nelson54.dominion.cards.types.Card;
import com.google.common.collect.ArrayListMultimap;

import java.util.HashMap;
import java.util.Set;


public class KingdomFactory {

    public Kingdom getKingdom() throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(kingdom);
        addVictoryCards(kingdom, 4);
        villageSquare(kingdom);

        return kingdom;
    }

    @Deprecated
    Kingdom getKingdomFromCards(Class<? extends Card>[] cards, int players) throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(kingdom);
        addVictoryCards(kingdom, players);

        for(Class<? extends Card> clazz: cards){
            addXCardsOfType(10, clazz, kingdom);
        }

        return kingdom;
    }

    Kingdom getKingdomFromCards(Set<Class<? extends Card>> cards, int players) throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(kingdom);
        addVictoryCards(kingdom, players);

        cards.forEach(clazz -> {
            try {
                addXCardsOfType(10, clazz, kingdom);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });

        return kingdom;
    }

    Kingdom getKingdomAllCards() throws IllegalAccessException, InstantiationException {
        Kingdom kingdom = new Kingdom();
        kingdom.setAllCards(new HashMap<>());
        kingdom.setCardMarket(ArrayListMultimap.create());

        addTreasureCards(kingdom);
        addVictoryCards(kingdom, 4);
        addKingdomCards(kingdom);

        return kingdom;
    }

    private void addTreasureCards(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(60, Copper.class, kingdom);
        addXCardsOfType(60, Silver.class, kingdom);
        addXCardsOfType(60, Gold.class, kingdom);
    }

    private void addVictoryCards(Kingdom kingdom, int players) throws InstantiationException, IllegalAccessException {
        int provinces = (players == 2) ? 8 : 12;

        addXCardsOfType(30, Estate.class, kingdom);
        addXCardsOfType(20, Duchy.class, kingdom);
        addXCardsOfType(provinces, Province.class, kingdom);
        addXCardsOfType(20, CurseCard.class, kingdom);
    }

    private void firstGame(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(10, Cellar.class, kingdom);
        addXCardsOfType(10, Market.class, kingdom);
        addXCardsOfType(10, Militia.class, kingdom);
        addXCardsOfType(10, Mine.class, kingdom);
        addXCardsOfType(10, Moat.class, kingdom);
        addXCardsOfType(10, Remodel.class, kingdom);
        addXCardsOfType(10, Smithy.class, kingdom);
        addXCardsOfType(10, Village.class, kingdom);
        addXCardsOfType(10, Woodcutter.class, kingdom);
        addXCardsOfType(10, Workshop.class, kingdom);
    }

    private void villageSquare(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(10, Bureaucrat.class, kingdom);
        addXCardsOfType(10, Cellar.class, kingdom);
        addXCardsOfType(10, Festival.class, kingdom);
        addXCardsOfType(10, Library.class, kingdom);
        addXCardsOfType(10, Market.class, kingdom);
        addXCardsOfType(10, Remodel.class, kingdom);
        addXCardsOfType(10, Smithy.class, kingdom);
        addXCardsOfType(10, ThroneRoom.class, kingdom);
        addXCardsOfType(10, Woodcutter.class, kingdom);
        addXCardsOfType(10, Village.class, kingdom);
    }

    private void sizeDistortion(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(10, Cellar.class, kingdom);
        addXCardsOfType(10, Chapel.class, kingdom);
        addXCardsOfType(10, Feast.class, kingdom);
        addXCardsOfType(10, Gardens.class, kingdom);
        addXCardsOfType(10, Laboratory.class, kingdom);
        addXCardsOfType(10, Thief.class, kingdom);
        addXCardsOfType(10, Village.class, kingdom);
        addXCardsOfType(10, Witch.class, kingdom);
        addXCardsOfType(10, Woodcutter.class, kingdom);
        addXCardsOfType(10, Workshop.class, kingdom);
    }

    private void bigMoney(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(10, Cellar.class, kingdom);
        addXCardsOfType(10, Chapel.class, kingdom);
        addXCardsOfType(10, Feast.class, kingdom);
        addXCardsOfType(10, Gardens.class, kingdom);
        addXCardsOfType(10, Laboratory.class, kingdom);
        addXCardsOfType(10, Thief.class, kingdom);
        addXCardsOfType(10, Village.class, kingdom);
        addXCardsOfType(10, Witch.class, kingdom);
        addXCardsOfType(10, Woodcutter.class, kingdom);
        addXCardsOfType(10, Workshop.class, kingdom);
    }

    private void addKingdomCards(Kingdom kingdom) throws InstantiationException, IllegalAccessException {
        addXCardsOfType(10, Smithy.class, kingdom);
        addXCardsOfType(10, Village.class, kingdom);
        addXCardsOfType(10, CouncilRoom.class, kingdom);
        addXCardsOfType(10, Feast.class, kingdom);
        addXCardsOfType(10, Festival.class, kingdom);
        addXCardsOfType(10, Gardens.class, kingdom);
        addXCardsOfType(10, Laboratory.class, kingdom);
        addXCardsOfType(10, Bureaucrat.class, kingdom);
        addXCardsOfType(10, Market.class, kingdom);
        addXCardsOfType(10, Moneylender.class, kingdom);
        addXCardsOfType(10, ThroneRoom.class, kingdom);
        addXCardsOfType(10, Witch.class, kingdom);
        addXCardsOfType(10, Woodcutter.class, kingdom);
        addXCardsOfType(10, Remodel.class, kingdom);
        addXCardsOfType(10, Mine.class, kingdom);
        addXCardsOfType(10, Workshop.class, kingdom);
        addXCardsOfType(10, Chapel.class, kingdom);
        addXCardsOfType(10, Adventurer.class, kingdom);
        addXCardsOfType(10, Chancellor.class, kingdom);
        addXCardsOfType(10, Militia.class, kingdom);
        addXCardsOfType(10, Cellar.class, kingdom);
        addXCardsOfType(10, Moat.class, kingdom);
        addXCardsOfType(10, Library.class, kingdom);
        addXCardsOfType(10, Spy.class, kingdom);
        addXCardsOfType(10, Thief.class, kingdom);
    }

    private void addXCardsOfType(int x, Class<? extends Card> clazz, Kingdom kingdom) throws IllegalAccessException, InstantiationException {
        for (; x > 0; x--) {
            Card card = clazz.newInstance();
            kingdom.getCardMarket().put(card.getName(), card);
            kingdom.getAllCards().put(card.getId().toString(), card);
        }
    }
}
