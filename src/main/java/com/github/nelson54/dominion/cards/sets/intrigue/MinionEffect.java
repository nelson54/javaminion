package com.github.nelson54.dominion.cards.sets.intrigue;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;
import java.util.stream.Collectors;


public class MinionEffect extends Effect {

    Set<Card> keepInHand;

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {


        return false;
    }
}
