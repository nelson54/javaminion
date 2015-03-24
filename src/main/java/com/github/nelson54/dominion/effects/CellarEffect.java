package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.HashSet;
import java.util.Set;

public class CellarEffect extends Effect {

    Set<Card> toDiscard;
    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        if(response.getCards() == null && toDiscard == null){
            toDiscard = new HashSet<>();
        }

        if(response.isDone()) {
            int toDraw = toDiscard.size();
            target.drawXCards(toDraw);
            target.discard(toDiscard);
            return true;
        } else {
            toDiscard.add(response.getCard());
        }

        return false;
    }
}
