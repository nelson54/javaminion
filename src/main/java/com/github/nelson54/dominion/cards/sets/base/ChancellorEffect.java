package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.choices.ChoiceResponse;


public class ChancellorEffect extends Effect {

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {

        Player player = response.getSource();
        if (response.isYes()) {
            player.getDiscard().addAll(player.getDeck());

            player.getDeck().clear();
        }

        return true;
    }

}