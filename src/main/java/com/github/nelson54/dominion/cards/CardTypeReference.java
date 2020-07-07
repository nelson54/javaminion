package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

public class CardTypeReference {
    private String id;
    private Class<? extends Card> clazz;
    private String name;

    private CardTypeReference(String id, String name, Class<? extends Card> clazz) {
        this.id = id;
        this.clazz = clazz;
        this.name = name;
    }

    private CardTypeReference(String name, Class<? extends Card> clazz) {
        this.clazz = clazz;
        this.name = name;
    }
    public static CardTypeReference of(String id, String name, Class<? extends Card> clazz) {
        return new CardTypeReference(id, name, clazz);
    }

    public static CardTypeReference of(String name, Class<? extends Card> clazz) {
        return new CardTypeReference(name, clazz);
    }

    public String getId() {
        return id;
    }

    public Class<? extends Card> getCardClass() {
        return clazz;
    }
}
