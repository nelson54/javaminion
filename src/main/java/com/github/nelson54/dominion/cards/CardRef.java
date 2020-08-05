package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class CardRef {
    private final Long id;
    private final String name;
    private final Boolean generic;


    public CardRef(Long id) {
        this.id = id;
        name = null;
        generic = false;
    }

    private CardRef(String name) {
        this.id = null;
        this.name = name;
        generic = true;
    }

    private CardRef(Long id, String name) {
        this.id = id;
        this.name = name;
        generic = true;
    }

    static CardRef ofCard(Card card) {
        return new CardRef(card.getId(), card.getName());
    }

    static CardRef toGenericRef(Card card) {
        return new CardRef(card.getName());
    }
}
