package com.github.nelson54.dominion.choices;

import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.effects.Effect;

public class Reaction {
    private Effect effect;
    private Card card;

    private Reaction(Effect effect) {
        this.effect = effect;
    }

    private Reaction(Card card) {
        this.card = card;
    }

    public Effect getEffect() {
        return effect;
    }

    public Card getCard() {
        return card;
    }

    public boolean hasEffect() {
        return effect != null;
    }

    public boolean hasCard() {
        return card != null;
    }

    public static Reaction from(Effect effect) {
        return new Reaction(effect);
    }

    public static Reaction from(Card card) {
        return new Reaction(card);
    }
}
