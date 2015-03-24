package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public class ChapelEffect extends Effect {

    private int trashed = 0;

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Choice choice = getChoice();
        Card option = response.getCard();

        if (getChoice().getCardOptions().contains(option)) {
            game.trashCard(option);
            trashed++;
        }

        return trashed == 4 || response.isDone();
    }

}
