package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.ChoiceResponse;

/**
 * Created by Derek on 3/10/2015.
 */
public class MilitiaEffect extends Effect {
    @Override
    boolean effect(ChoiceResponse response, Turn turn, Game game) {
        return false;
    }
}
