package com.github.nelson54.dominion.choices;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.effects.Effect;

import java.util.Set;

/**
 * Created by dnelson on 3/1/2015.
 */
public class ChoiceResponse<T> {

    Player source;
    Effect effect;

    ChoiceType choiceType;
    Set<Card> cards;

    T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Player getSource() {
        return source;
    }

    public void setSource(Player source) {
        this.source = source;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public ChoiceType getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(ChoiceType choiceType) {
        this.choiceType = choiceType;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
