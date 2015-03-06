package com.github.nelson54.dominion.cards;

import java.util.Set;
import java.util.stream.Collectors;

public class Cards {

    public static Set<Card> cardsOfType(Set<Card> cards, Class<? extends Card> clazz) {
        return cards.stream()
                .filter(clazz::isInstance)
                .collect(Collectors.toSet());
    }

}
