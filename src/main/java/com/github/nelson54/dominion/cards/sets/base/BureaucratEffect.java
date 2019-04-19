package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Optional;

public class BureaucratEffect extends Effect {
    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        final boolean resolved;

        Optional<Long> choice = response.getChoices().stream().findAny();

        choice.ifPresent((id) -> target.putOnTopOfDeck(target.getAllCards().get(id)));

        return true;
    }
}
