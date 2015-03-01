package com.github.nelson54.dominion.effects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public abstract class Effect<T> {

    @JsonBackReference
    Player owner;
    @JsonBackReference
    Player target;

    @JsonBackReference
    Card source;

    boolean cancelled;

    @JsonBackReference
    Choice<T> choice;

    public Effect(){
        cancelled = false;
    }

    public void resolve(ChoiceResponse response){
        if(!cancelled){
            effect(response);
        }
    }

    abstract void effect(ChoiceResponse response);

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Card getSource() {
        return source;
    }

    public void setSource(Card source) {
        this.source = source;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Choice<T> getChoice() {
        return choice;
    }

    public void setChoice(Choice<T> choice) {
        this.choice = choice;
    }
}
