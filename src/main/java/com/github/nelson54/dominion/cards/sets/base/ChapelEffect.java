package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;

public class ChapelEffect extends Effect {

    private int trashed = 0;

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Card option = response.getCard();

        if (getChoice().getCardOptions().contains(option)) {
            game.trashCard(option);
            trashed++;
        }

        return trashed == 4 || response.isDone();
    }

}
