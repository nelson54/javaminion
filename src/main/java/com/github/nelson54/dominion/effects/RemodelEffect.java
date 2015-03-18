package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.CardState;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public class RemodelEffect extends Effect {

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Card card = response.getCard();
        Choice choice = getChoice();

        if ( card != null && choice.getState() == CardState.TRASHING_CARD) {
            game.trashCard(card);
            return false;
        } else if (choice.getState() == CardState.GAINING_CARD) {
            Card gainedCard = response.getCard();
            game.giveCardToPlayer(gainedCard.getName(), response.getSource());
            return true;
        }

        return false;
    }
}
