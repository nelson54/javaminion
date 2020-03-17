package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.game.choices.Reaction;
import com.github.nelson54.dominion.exceptions.MissingEffectException;

public class MoatRevealEffect extends Effect {
    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
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
