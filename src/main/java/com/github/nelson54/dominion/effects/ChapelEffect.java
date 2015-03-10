package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public class ChapelEffect extends Effect {

    int trashed = 0;

    @Override
    boolean effect(ChoiceResponse response, Turn turn, Game game) {

        Card option = game.getAllCards().get(response.getCard().getId().toString());

        if(getChoice().getCardOptions().contains(option)){
            game.trashCard(option);
            trashed++;
        }

        return trashed == 4;
    }

}
