package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public class RemodelEffect extends Effect {
    private Card trashedCard;

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        if (trashedCard == null && response.getCard() != null) {
            trashedCard = response.getCard();
            game.trashCard(trashedCard);
            return false;
        } else if (response.getCard() != null) {
            Card gainedCard = response.getCard();
            game.giveCardToPlayer(gainedCard.getName(), response.getSource());
            return true;
        }

        return false;
    }
}
