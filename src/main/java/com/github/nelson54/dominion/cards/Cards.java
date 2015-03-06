package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Cards {

    public static Set<Card> cardsOfType(Set<Card> cards, Class<? extends Card> clazz) {
        return cards.stream()
                .filter(clazz::isInstance)
                .collect(Collectors.toSet());
    }

    public static Set<Card> cardsRemainingInHand(Player player) {
        Collection<Card> cardsInPlay = player.getCurrentTurn().getPlay().values();
        Set<Card> cardsInHand = new HashSet<>();
        cardsInHand.addAll(player.getHand());
        cardsInHand.removeAll(cardsInPlay);
        return cardsInHand;
    }

}
