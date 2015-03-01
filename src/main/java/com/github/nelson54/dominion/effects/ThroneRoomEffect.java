package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;

/**
 * Created by dnelson on 3/1/2015.
 */
public class ThroneRoomEffect extends Effect<ActionCard> {

    @Override
    void effect(ActionCard response) {
        Game game = getTarget().getGame();
        Turn turn = game.getTurn();
        if(getChoice().getOptions().contains(response)){
            turn.playCard(response, getTarget(), game);
            response.apply(getTarget(), game);
        }
    }

}