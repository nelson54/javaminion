package com.github.nelson54.dominion.choices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.*;
import com.github.nelson54.dominion.effects.Effect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Choice {

    private UUID id;
    private CardState state;
    @JsonIgnore
    private Player target;
    @JsonIgnore
    private Player owner;
    @JsonIgnore
    private Game game;
    private Card source;
    private Card displayCard;
    private String message;

    private Set<String> options;

    private boolean isComplete;
    private boolean isRequired;
    private boolean isDialog;

    private Set<String> textOptions;
    private Set<Card> cardOptions;

    private Set<String> currentlySelected;

    private OptionType expectedAnswerType;

    @JsonIgnore
    private Choice parentChoice;
    @JsonIgnore
    private ChoiceResponse response;

    private byte number;
    private Range range;

    @JsonIgnore
    private Effect effect;
    private Set<String> choices;

    public Choice(Player target, Card source) {

        this.id = UUID.randomUUID();
        this.target = target;
        this.source = source;
        this.owner = source.getOwner();
        this.options = new HashSet<>();
        //TODO isDialog should not be true
        this.setIsDialog(true);
        this.choices = new HashSet<>();
    }

    public void setIsDialog(boolean isDialog) {
        this.isDialog = isDialog;
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
        choiceResponse.getChoice();

        if (choiceResponse.isDone()) {
            setComplete(true);
        }

        setComplete(effect.resolve(choiceResponse, target, turn, turn.getGame()));

        if (!isComplete) {
            Player player = this.getTarget();
            Game game = player.getGame();
            ComplexActionCard complexCard = (ComplexActionCard) source;

            player.getChoices().remove(this);
            complexCard.addChoice(player, game, choiceResponse, this);
        }

        resolveIfComplete(turn);
    }

    public Player getTarget() {
        return target;
    }

    public void resolveIfComplete(Turn turn) {
        Set<Choice> choices = target.getChoices();
        Set<Choice> resolved = turn.getResolvedChoices();

        if (isComplete) {
            choices.remove(this);
            resolved.add(this);

            if (choices.size() > 0){
               choices.stream().findFirst().ifPresent((choice)-> choice.getTarget().onChoice());
            } else {
                game.getTurn().setPhase(Phase.ACTION);
                getOwner().onActionPhase();
            }


        }
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getTextOptions() {
        return textOptions;
    }

    public void setTextOptions(Set<String> textOptions) {
        this.textOptions = textOptions;
    }

    @Deprecated
    public Set<Card> getCardOptions() {
        return cardOptions;
    }

    @Deprecated
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

    public void setResponse(ChoiceResponse response) {
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

    public Card getDisplayCard() {
        return displayCard;
    }

    public void setDisplayCard(Card displayCard) {
        this.displayCard = displayCard;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

    public boolean isDialog() {
        return isDialog;
    }

    public void setChoices(Set<String> choices) {
        this.choices = choices;
    }

    public Set<String> getChoices() {
        return choices;
    }
}
