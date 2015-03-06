package com.github.nelson54.dominion.choices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.effects.Effect;

import java.util.Set;

/**
 * Created by dnelson on 3/1/2015.
 */
public class ChoiceResponse {

    String targetChoice;
    String decisionId;

    @JsonIgnore
    Player source;
    @JsonIgnore
    Effect effect;

    ChoiceType choiceType;

    Card card;
    Set<Card> cards;
    String choice;
    Set<String> choices;


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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCardsChoice(Set<Card> cardsChoice) {
        this.cards = cardsChoice;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Set<String> getChoices() {
        return choices;
    }

    public void setChoices(Set<String> choiceTexts) {
        this.choices = choiceTexts;
    }

    public String getTargetChoice() {
        return targetChoice;
    }

    public void setTargetChoice(String targetChoice) {
        this.targetChoice = targetChoice;
    }

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }
}
