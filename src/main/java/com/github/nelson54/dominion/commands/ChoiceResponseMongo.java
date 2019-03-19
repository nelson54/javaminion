package com.github.nelson54.dominion.commands;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class ChoiceResponseMongo {
    private String targetChoice;
    private String decisionId;
    private String message;
    private Boolean done;
    private Boolean yesOrNo;
    private Long card;
    private Set<Long> cards;
    private String choice;
    private Set<Long> choices;

    public ChoiceResponseMongo() {
    }

    public ChoiceResponse toChoiceResponse(Game game) {
        ChoiceResponse cr = new ChoiceResponse();
        cr.setTargetChoice(targetChoice);
        cr.setDecisionId(decisionId);
        cr.setMessage(message);
        cr.setDone(done);
        cr.setYes(yesOrNo);
        if (card != null) {
            cr.setCard(game.getAllCards().get(card));
        }

        Set<Card> cards = null;
        if (this.cards != null) {
            cards = this.cards.stream()
                    .map(id -> game.getAllCards().get(id)).collect(Collectors.toSet());
        }

        cr.setCards(cards);

        cr.setChoice(choice);
        cr.setChoices(choices);

        return cr;
    }

    public static ChoiceResponseMongo ofChoiceResponse(ChoiceResponse choiceResponse) {
        ChoiceResponseMongo choiceResponseMongo = new ChoiceResponseMongo();

        choiceResponseMongo.setTargetChoice(choiceResponse.getTargetChoice());
        choiceResponseMongo.setDecisionId(choiceResponse.getDecisionId());
        choiceResponseMongo.setMessage(choiceResponse.getMessage());
        choiceResponseMongo.setDone(choiceResponse.isDone());
        choiceResponseMongo.setYesOrNo(choiceResponse.isYes());
        choiceResponseMongo.setChoice(choiceResponse.getChoice());
        choiceResponseMongo.setChoices(choiceResponse.getChoices());

        if (choiceResponse.getCard() != null) {
            choiceResponseMongo.setCard(choiceResponse.getCard().getId());
        }

        if (choiceResponse.getCard() != null) {
            choiceResponseMongo.setCard(choiceResponse.getCard().getId());
        }

        if (choiceResponse.getCards() != null) {
            Set<Long> cardIds = choiceResponse
                    .getCards().stream().map(Card::getId).collect(Collectors.toSet());
            choiceResponseMongo.setCards(cardIds);
        }

        return choiceResponseMongo;

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

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(Boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public Long getCard() {
        return card;
    }

    public void setCard(Long card) {
        this.card = card;
    }

    public Set<Long> getCards() {
        return cards;
    }

    public void setCards(Set<Long> cards) {
        this.cards = cards;
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

    public void setChoices(Set<Long> choices) {
        this.choices = choices;
    }
}
