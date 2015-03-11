package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Derek on 3/10/2015.
 */
public class MilitiaEffect extends Effect {
    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Set<Card> toDiscard = target.getHand().stream()
                .filter(card -> response.getCards().contains(card))
                .collect(Collectors.toSet());

        target.getHand().removeAll(toDiscard);
        target.getDiscard().addAll(toDiscard);

        return true;
    }
}
