package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;

import java.util.Set;
import java.util.stream.Collectors;


public class MilitiaEffect extends Effect {

    Set<Card> keepInHand;

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        if (response != null && response.getChoices() != null && target.getHand().size() > 3) {
            Set<Card> cards = response
                    .getChoices().stream()
                    .map(id -> game.getAllCards().get(id))
                    .collect(Collectors.toSet());

            target.discard(cards);
        }

        return target.getHand().size() <= 3;

    }
}
