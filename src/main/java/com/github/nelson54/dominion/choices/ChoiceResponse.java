package com.github.nelson54.dominion.choices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.Effect;

import java.util.HashSet;
import java.util.Set;


public class ChoiceResponse {

    private String targetChoice;
    private String decisionId;

    private String message;

    @JsonIgnore
    private Player source;
    @JsonIgnore
    private Effect effect;

    private boolean done;

    private boolean yesOrNo;
    private Card card;
    private Set<Card> cards;
    private String choice;
    private Set<Long> choices;
    private Reaction reaction;


    private int choiceStringNumber;

    public ChoiceResponse() {
        choices = new HashSet<>();
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
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

    public Set<Long> getChoices() {
        return choices;
    }

    public void setChoices(Set<Long> choiceTexts) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isYes() {
        return yesOrNo;
    }

    public void setYes(boolean isYes) {
        this.yesOrNo = isYes;
    }

    public int getChoiceStringNumber() {
        return choiceStringNumber;
    }

    public void setChoiceStringNumber(int choiceStringNumber) {
        this.choiceStringNumber = choiceStringNumber;
    }

    public Reaction getReaction() {
        return reaction;
    }
}
