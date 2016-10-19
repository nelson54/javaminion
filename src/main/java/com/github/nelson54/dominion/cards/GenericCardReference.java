package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

public class GenericCardReference {

    private Class<? extends Card> clazz;
    private String name;

    private GenericCardReference(String name, Class<? extends Card> clazz) {
        this.clazz = clazz;
        this.name = name;
    }

    public static GenericCardReference of(String name, Class<? extends Card> clazz) {
        return new GenericCardReference(name, clazz);
    }

    public static GenericCardReference of(Card card) {
        return new GenericCardReference(card.getName(), card.getClass());
    }

    public Class<? extends Card> getCardClass() {
        return clazz;
    }
}
