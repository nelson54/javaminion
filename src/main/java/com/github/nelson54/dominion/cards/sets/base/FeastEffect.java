package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;

public class FeastEffect extends Effect {


    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        Card option = game.getAllCards().get(response.getCard().getId());

        if (getChoice().getOptions().contains(option.getId())) {
            game.giveCardToPlayer(option.getName(), getTarget());
            return true;
        }

        return false;
    }

}
