package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

public class LocalCardReference extends Card {
    public LocalCardReference(String id){
        this.setId(id);
    }

    LocalCardReference(String id, String name){
        this.setId(id);
        this.setName(name);
    }
}
