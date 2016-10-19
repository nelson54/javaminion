package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class GameCardSet {
    private Set<GenericCardReference> cards;

    private GameCardSet(Set<GenericCardReference> cards) {
        this.cards = cards;
    }

    public static GameCardSet of(GenericCardReference ... refs) {
        return new GameCardSet(Arrays.stream(refs).collect(Collectors.toSet()));
    }

    public Set<Class<? extends Card>> getCardClasses() {
        return cards
                .stream()
                .map(GenericCardReference::getCardClass)
                .collect(Collectors.toSet());
    }
}
