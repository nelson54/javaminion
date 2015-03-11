package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;

public class CellarEffect extends Effect {
    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        Set<Card> toDiscard = response.getCards();
        int toDraw = toDiscard.size();
        target.drawXCards(toDraw);
        target.discard(toDiscard);

        return true;
    }
}
