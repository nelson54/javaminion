package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;

public class FeastEffect extends Effect<Card> {

    @Override
    void effect(Card response) {
        Game game = getTarget().getGame();
        if(getChoice().getOptions().contains(response)){
            game.giveCardToPlayer(response.getName(), getTarget());
        }
    }
}
