package com.github.nelson54.dominion.choices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.ComplexActionCard;
import com.github.nelson54.dominion.effects.Effect;

import java.util.Set;
import java.util.UUID;

public class Choice {
    private UUID id;
    @JsonIgnore
    private
    Player target;
    @JsonIgnore
    private
    Player owner;
    @JsonIgnore
    private
    Game game;
    private Card source;

    private String message;
    private ChoiceType choiceType;

    private boolean isComplete;
    private boolean isRequired;

    private Set<String> textOptions;
    private Set<Card> cardOptions;

    private OptionType expectedAnswerType;

    @JsonIgnore
    private
    Choice parentChoice;
    @JsonIgnore
    private
    ChoiceResponse response;

    private byte number;
    private Range range;

    @JsonIgnore
    private
    Effect effect;

    public Choice(Player target, Card source) {

        this.id = UUID.randomUUID();
        this.target = target;
        this.source = source;
        this.owner = source.getOwner();
    }

    public void bind(Effect effect) {
        this.effect = effect;

        effect.setSource(source);
        effect.setTarget(target);
        effect.setOwner(owner);
        effect.setChoice(this);
    }

    public void apply(ChoiceResponse choiceResponse, Turn turn) {
        setResponse(choiceResponse);
        effect.resolve(choiceResponse, turn, turn.getGame());

        resolveIfComplete(turn);
    }

    void resolveIfComplete(Turn turn) {
        Set<Choice> choices = turn.getUnresolvedChoices();
        Set<Choice> resolved = turn.getResolvedChoices();

        if (isComplete) {
            choices.remove(this);
            resolved.add(this);
            if (choices.size() == 0) {
                turn.setPhase(Phase.ACTION);
            }
        } else if (source instanceof ComplexActionCard) {
            Player player = this.getTarget();
            Game game = player.getGame();
            ComplexActionCard complexCard = (ComplexActionCard) source;

            complexCard.addChoice(player, game);
            choices.remove(this);
        }


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChoiceType getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(ChoiceType choiceType) {
        this.choiceType = choiceType;
    }

    public Set<String> getTextOptions() {
        return textOptions;
    }

    public void setTextOptions(Set<String> textOptions) {
        this.textOptions = textOptions;
    }

    public Set<Card> getCardOptions() {
        return cardOptions;
    }

    public void setCardOptions(Set<Card> cardOptions) {
        this.cardOptions = cardOptions;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Card getSource() {
        return source;
    }

    public void setSource(Card source) {
        this.source = source;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Choice getParentChoice() {
        return parentChoice;
    }

    public void setParentChoice(Choice parentChoice) {
        this.parentChoice = parentChoice;
    }

    public ChoiceResponse getResponse() {
        return response;
    }

    void setResponse(ChoiceResponse response) {
        this.response = response;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public OptionType getExpectedAnswerType() {
        return expectedAnswerType;
    }

    public void setExpectedAnswerType(OptionType expectedAnswerType) {
        this.expectedAnswerType = expectedAnswerType;
    }
}
