package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

public class CardReference extends Card {
    public CardReference(Long id){
        super(id);
    }

    CardReference(Long id, String name){
        super(id);
        this.setName(name);
    }
}
