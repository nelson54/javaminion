package com.github.nelson54.dominion.cards.sets.intrigue;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;

import java.util.Set;


public class MinionEffect extends Effect {

    Set<Card> keepInHand;

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {


        return false;
    }
}
