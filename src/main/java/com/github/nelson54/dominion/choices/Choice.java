package com.github.nelson54.dominion.choices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.effects.Effect;

import java.util.Set;
import java.util.UUID;

public class Choice<T> {
    UUID id;
    @JsonIgnore
    Player target;
    @JsonIgnore
    Player owner;
    Card source;

    String message;
    ChoiceType choiceType;
    Set<T> options;

    byte number;
    Range range;

    Effect<T> effect;

    public Choice(Player target, Card source){

        this.id = UUID.randomUUID();
        this.target = target;
        this.source = source;
        this.owner = source.getOwner();
    }

    public void bind(Effect<T> effect){
        this.effect = effect;
        effect.setSource(source);
        effect.setTarget(target);
        effect.setOwner(owner);
        effect.setChoice(this);
    }

    public void apply(ChoiceResponse choiceResponse, Turn turn){
        Set<Choice> choices = turn.getUnresolvedChoices();
        Set<Choice> resolved = turn.getResolvedChoices();

        choices.remove(this);
        resolved.add(this);

        effect.resolve(choiceResponse);

        if(choices.size() == 0){
            turn.setPhase(Phase.ACTION);
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

    public Set<T> getOptions() {
        return options;
    }

    public void setOptions(Set<T> options) {
        this.options = options;
    }

    public Effect<T> getEffect() {
        return effect;
    }

    public void setEffect(Effect<T> effect) {
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

    public Player getTarget() {
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
}
