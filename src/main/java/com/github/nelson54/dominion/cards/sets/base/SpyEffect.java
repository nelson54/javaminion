package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;

/**
 * Created by Derek on 3/17/2015.
 */
public class SpyEffect extends Effect {

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Card displayCard = getChoice().getDisplayCard();
        Player owner = displayCard.getOwner();

        if (response.isYes()) {
            owner.discard(displayCard);
            game.log(owner.getName() + " discarded " + displayCard.getName() + " from Spy.");
        } else {
            owner.putOnTopOfDeck(displayCard);
            game.log(owner.getName() + " kept " + displayCard.getName() + " on top of deck from Spy.");
        }

        return true;
    }
}
