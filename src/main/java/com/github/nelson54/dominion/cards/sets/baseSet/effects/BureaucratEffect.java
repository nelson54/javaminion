package com.github.nelson54.dominion.cards.sets.baseSet.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.google.common.collect.Multimap;

public class BureaucratEffect extends Effect {
    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Card card = response.getCard();
        target.putOnTopOfDeck(card);
        return true;
    }

    @Override
    void onNoValidTarget(Choice choice, Player target, Turn turn, Game game) {
        Multimap<String, Card> revealed = turn.getRevealed();
        revealed.putAll(target.getId(), target.getHand());
        target.getHand();
    }

}
