package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Derek on 3/10/2015.
 */
public class MilitiaEffect extends Effect {

    Set<Card> keepInHand;

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        if(keepInHand == null){
            keepInHand = new HashSet<>();
        }

        if(keepInHand.size() == 3) {
            Set<Card> toDiscard = target.getHand().stream()
                    .filter(card -> !keepInHand.contains(card))
                    .collect(Collectors.toSet());

            target.getHand().removeAll(toDiscard);
            target.getDiscard().addAll(toDiscard);

            return true;
        } else {
            keepInHand.add(response.getCard());
        }

        return false;
    }
}
