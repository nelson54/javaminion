package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

/**
 * Created by Derek on 3/17/2015.
 */
public class SpyEffect extends Effect {

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Card displayCard = getChoice().getDisplayCard();
        Player owner = displayCard.getOwner();

        if(response.getChoice().startsWith("Discard")){
            owner.discard(displayCard);
        } else {
            owner.putOnTopOfDeck(displayCard);
        }

        return false;
    }
}
