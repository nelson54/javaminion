package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

import java.util.Set;

public class CardUtils {

    public static int numberOfCardsByName(Set<Card> cards, String name) {
        return (int) cards.stream()
                .filter(c -> c.getName().equals(name))
                .count();
    }

}
