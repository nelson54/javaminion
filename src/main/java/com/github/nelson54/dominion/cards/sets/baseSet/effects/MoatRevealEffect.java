package com.github.nelson54.dominion.cards.sets.baseSet.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.sets.baseSet.Moat;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.Reaction;
import com.github.nelson54.dominion.exceptions.MissingEffectException;

public class MoatRevealEffect extends Effect {
    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Reaction reaction = response.getReaction();

        if (reaction.hasEffect()) {
            reaction.getEffect().setCancelled(response.isYes());
            return true;

        } else {
            throw new MissingEffectException();
        }
    }

    public static MoatRevealEffect create(Moat card, Choice choice) {
        MoatRevealEffect moatEffect = new MoatRevealEffect();
        moatEffect.setOwner(card.getOwner());
        moatEffect.setSource(card);
        moatEffect.setTarget(card.getOwner());
        moatEffect.setChoice(choice);
        return moatEffect;
    }
}
