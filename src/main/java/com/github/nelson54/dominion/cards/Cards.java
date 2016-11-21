package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ActionAttackCard;
import com.github.nelson54.dominion.cards.types.Card;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Cards {

    public static Set<Card> ofType(Collection<Card> cards, Class<? extends Card> clazz) {
        return cards.stream()
                .filter(clazz::isInstance)
                .collect(Collectors.toSet());
    }

    public static Set<Card> ofType(Collection<Card> cards, CardType cardType) {
        return cards.stream()
                .filter(card -> card.isType(cardType))
                .collect(Collectors.toSet());
    }

    public static Set<Card> cardsRemainingInHand(Player player) {
        Collection<Card> cardsInPlay = player.getCurrentTurn().getPlay();
        Set<Card> cardsInHand = new HashSet<>();
        cardsInHand.addAll(player.getHand());
        cardsInHand.removeAll(cardsInPlay);
        return cardsInHand;
    }

    public static Set<String> getIds(Collection<Card> cards){
        return cards.stream()
                .map(Card::getId)
                .collect(Collectors.toSet());
    }

    public static Card ofId(String id){
        return new CardReference(id);
    }

    public static boolean isAttackCard (Card card) {
        return card.getClass().isAssignableFrom(ActionAttackCard.class);
    }
}
