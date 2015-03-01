package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public class FeastEffect extends Effect<Card> {

    @Override
    void effect(ChoiceResponse response) {
        Game game = getTarget().getGame();
        Card option = game.getAllCards().get(response.getCard().getId().toString());

        if(getChoice().getOptions().contains(option)){
            game.giveCardToPlayer(option.getName(), getTarget());
        }
    }

}
