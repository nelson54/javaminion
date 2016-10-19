package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;


@JsonAutoDetect
public class Kingdom {

    @JsonProperty
    private
    Multimap<String, Card> cardMarket;

    private Map<String, Card> allCards;

    Collection<Card> getCardsByName(String name) {
        return cardMarket.get(name);
    }

    public int getNumberOfRemainingCardsByName(String name) {
        if (!cardMarket.containsKey(name))
            return 0;
        else
            return getCardsByName(name).size();
    }

    public Multimap<String, Card> getCardMarket() {
        return cardMarket;
    }

    void setCardMarket(Multimap<String, Card> cardMarket) {
        this.cardMarket = cardMarket;
    }

    public Map<String, Card> getAllCards() {
        return allCards;
    }

    void setAllCards(Map<String, Card> allCards) {
        this.allCards = allCards;
    }

    private Set<Card> getCardOfEachType() {
        Set<Card> cardsOfEachType = new HashSet<>();

        cardMarket.keySet().stream()
                .map(id -> new ArrayList<>(cardMarket.get(id)))
                .map(cards -> cards.get(0))
                .forEach(cardsOfEachType::add);

        return cardsOfEachType;
    }

    public Set<Card> getTreasureCards() {
        return getCardOfEachType().stream()
                .filter(card -> card instanceof TreasureCard)
                .collect(Collectors.toSet());
    }
}
