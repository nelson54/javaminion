package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class GameCardSet {
    private Set<CardTypeReference> cards;

    private GameCardSet(Set<CardTypeReference> cards) {
        this.cards = cards;
    }

    public static GameCardSet of(Collection<CardTypeReference> cardTypes) {
        return new GameCardSet(new HashSet<>(cardTypes));
    }

    public static GameCardSet of(CardTypeReference... refs) {
        return of(Arrays.stream(refs).collect(Collectors.toSet()));
    }

    public Set<CardTypeReference> getCards() {
        return cards;
    }

    public Set<Class<? extends Card>> getCardClasses() {
        return cards
                .stream()
                .map(CardTypeReference::getCardClass)
                .collect(Collectors.toSet());
    }
}
