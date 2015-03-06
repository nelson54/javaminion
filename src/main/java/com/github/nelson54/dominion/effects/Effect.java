package com.github.nelson54.dominion.effects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

public abstract class Effect {

    @JsonBackReference
    Player owner;
    @JsonBackReference
    Player target;

    @JsonBackReference
    Card source;

    boolean cancelled;

    @JsonBackReference
    Choice choice;

    public Effect(){
        cancelled = false;
    }

    public void resolve(ChoiceResponse response, Turn turn, Game game){
        if(!cancelled){
            choice.setComplete(effect(response, turn, game));
        }
    }

    abstract boolean effect(ChoiceResponse response, Turn turn, Game game);

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

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
