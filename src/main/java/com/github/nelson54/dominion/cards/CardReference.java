package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

public class CardReference extends Card {
    public CardReference(String id){
        this.setId(id);
    }

    CardReference(String id, String name){
        this.setId(id);
        this.setName(name);
    }
}
