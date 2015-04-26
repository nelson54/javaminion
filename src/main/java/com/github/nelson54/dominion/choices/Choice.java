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

    UUID id;
    CardState state;
    @JsonIgnore
    Player target;
    @JsonIgnore
    Player owner;
    @JsonIgnore
    Game game;
    Card source;
    Card displayCard;
    String message;

    Set<String> options;

    boolean isComplete;
    boolean isRequired;
    boolean isDialog;

    Set<String> textOptions;
    Set<Card> cardOptions;

    OptionType expectedAnswerType;

    @JsonIgnore
    Choice parentChoice;
    @JsonIgnore
    ChoiceResponse response;

    byte number;
    Range range;

    @JsonIgnore
    Effect effect;

    public Choice(Player target, Card source) {

        this.id = UUID.randomUUID();
        this.target = target;
        this.source = source;
        this.owner = source.getOwner();
        this.options = new HashSet<>();
        //TODO isDialog should not be true
        this.setIsDialog(true);
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

            complexCard.addChoice(player, game);
            player.getChoices().remove(this);
            player.onChoice();
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

            if(turn.getActionPool() == 0 || Cards.cardsOfType(turn.getPlayer().getHand(), ActionCard.class).size() == 0) {
                turn.endPhase();
            } else if (choices.size() == 0) {
                turn.getPlayer().onActionPhase();
                game.getTurn().setPhase(Phase.ACTION);
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
}
