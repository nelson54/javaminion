package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;


public class ThroneRoomEffect extends Effect {

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        if(response.isDone()){

            return true;
        } else {
            Card option = game.getAllCards().get(response.getCard().getId().toString());
            ActionCard actionCard = null;

            if (option instanceof ActionCard) {
                actionCard = (ActionCard) option;
            }

            if (actionCard != null) {
                actionCard.apply(getTarget(), game);
                actionCard.apply(getTarget(), game);

                turn.getPlay().add(actionCard);
                turn.getPlay().add(actionCard);

                target.getHand().remove(actionCard);
                return true;
            }
        }

        return false;
    }

}