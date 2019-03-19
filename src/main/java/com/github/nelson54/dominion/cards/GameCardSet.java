package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.exceptions.InvalidCardSetName;

import java.util.*;
import java.util.stream.Collectors;


public class GameCardSet {
    private Set<CardTypeReference> cards;

    private GameCardSet(LinkedHashSet<CardTypeReference> cards) {
        this.cards = cards;
    }

    public static GameCardSet of(List<CardTypeReference> cardTypes) {
        return new GameCardSet(new LinkedHashSet<>(cardTypes));
    }

    public static GameCardSet of(CardTypeReference... refs) {
        return of(Arrays.stream(refs).collect(Collectors.toList()));
    }

    public Set<CardTypeReference> getCards() {
        return cards;
    }

    public static GameCardSet byName(String name) {
        GameCardSet gameCardSet;
        GameCards gameCards = GameCards.ofName(name);
        if (gameCards != null) {
            gameCardSet = gameCards.getGameCardSet();
        } else {
            throw new InvalidCardSetName();
        }

        return gameCardSet;
    }

    public Set<Class<? extends Card>> getCardClasses() {
        return cards
                .stream()
                .map(CardTypeReference::getCardClass)
                .collect(Collectors.toSet());
    }
}
