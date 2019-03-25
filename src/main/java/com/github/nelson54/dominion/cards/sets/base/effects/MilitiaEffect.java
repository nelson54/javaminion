package com.github.nelson54.dominion.cards.sets.base.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;
import java.util.stream.Collectors;


public class MilitiaEffect extends Effect {

    Set<Card> keepInHand;

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        if (response != null && response.getChoices() != null && target.getHand().size() > 3) {
            Set<Card> cards = response
                    .getChoices().stream()
                    .map(id -> Cards.ofId(game, id))
                    .collect(Collectors.toSet());

            target.discard(cards);
        }

        return target.getHand().size() <= 3;

    }
}
