package com.github.nelson54.dominion.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import com.google.common.collect.ArrayListMultimap;

import java.util.*;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Kingdom {

    @JsonProperty
    private ArrayListMultimap<String, Card> cardMarket;

    @JsonProperty
    private Map<Long, Card> allCards;

    Collection<Card> getCardsByName(String name) {
        return cardMarket.get(name);
    }

    public int getNumberOfRemainingCardsByName(String name) {
        if (!cardMarket.containsKey(name)) {
            return 0;
        }

        return getCardsByName(name).size();
    }

    public ArrayListMultimap<String, Card> getCardMarket() {
        return cardMarket;
    }

    public void setCardMarket(ArrayListMultimap<String, Card> cardMarket) {
        this.cardMarket = cardMarket;
    }

    public Map<Long, Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(Map<Long, Card> allCards) {
        this.allCards = allCards;
    }

    public Set<Card> getTreasureCards() {
        return getCardOfEachType().stream()
                .filter(card -> card instanceof TreasureCard)
                .collect(Collectors.toSet());
    }

    private Set<Card> getCardOfEachType() {
        Set<Card> cardsOfEachType = new HashSet<>();

        cardMarket.keySet().stream()
                .map(id -> new ArrayList<>(cardMarket.get(id)))
                .map(cards -> cards.get(0))
                .forEach(cardsOfEachType::add);

        return cardsOfEachType;
    }
}
