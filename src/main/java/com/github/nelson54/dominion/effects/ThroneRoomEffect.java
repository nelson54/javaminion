package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;


public class ThroneRoomEffect extends Effect {

    @Override
    boolean effect(ChoiceResponse response, Turn turn, Game game) {

        Card option = game.getAllCards().get(response.getCard().toString());
        ActionCard actionCard = null;

        if(option instanceof ActionCard){
            actionCard = (ActionCard)option;
        }

        if(getChoice().getCardOptions().contains(response)){
            turn.playCard(actionCard, getTarget(), game);
            //response.apply(getTarget(), game);
        }

        return false;
    }

}