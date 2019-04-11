package com.github.nelson54.dominion.cards.sets.base.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.google.common.collect.Multimap;

import java.util.Optional;

public class BureaucratEffect extends Effect {
    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        response.getChoices().stream()
                .findAny()
                .ifPresent((id) -> target.putOnTopOfDeck(target.getAllCards().get(id)));

        return true;
    }

    @Override
    void onNoValidTarget(Choice choice, Player target, Turn turn, Game game) {
        game.revealCardsFromHand(target, target.getHand());
    }
}
