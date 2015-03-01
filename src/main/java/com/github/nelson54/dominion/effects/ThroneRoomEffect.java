package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

/**
 * Created by dnelson on 3/1/2015.
 */
public class ThroneRoomEffect extends Effect<ActionCard> {

    @Override
    void effect(ChoiceResponse response) {
        Game game = getTarget().getGame();
        Turn turn = game.getTurn();

        Card option = game.getAllCards().get(response.getCard().toString());;
        ActionCard actionCard = null;

        if(option instanceof ActionCard){
            actionCard = (ActionCard)option;
        }

        if(getChoice().getOptions().contains(response)){
            turn.playCard(actionCard, getTarget(), game);
            //response.apply(getTarget(), game);
        }
    }

}