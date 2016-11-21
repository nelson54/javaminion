package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

public class CardTypeReference {

    private Class<? extends Card> clazz;
    private String name;

    private CardTypeReference(String name, Class<? extends Card> clazz) {
        this.clazz = clazz;
        this.name = name;
    }

    public static CardTypeReference of(String name, Class<? extends Card> clazz) {
        return new CardTypeReference(name, clazz);
    }

    public static CardTypeReference of(Card card) {
        return new CardTypeReference(card.getName(), card.getClass());
    }

    public Class<? extends Card> getCardClass() {
        return clazz;
    }
}
