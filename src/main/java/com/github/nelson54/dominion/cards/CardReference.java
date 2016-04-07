package com.github.nelson54.dominion.cards;

class CardReference extends Card {
    CardReference(String id){
        this.setId(id);
    }

    CardReference(String id, String name){
        this.setId(id);
        this.setName(name);
    }
}
