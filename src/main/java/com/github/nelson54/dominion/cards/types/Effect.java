package com.github.nelson54.dominion.cards.types;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.exceptions.MissingCardException;
import com.github.nelson54.dominion.exceptions.MissingEffectException;

public abstract class Effect {

    @JsonIgnore
    private boolean noValidTarget;

    @JsonBackReference(value = "owner")
    private Player owner;

    @JsonBackReference(value = "target")
    private Player target;

    @JsonBackReference(value = "source")
    private Card source;

    private boolean cancelled;

    @JsonBackReference(value = "choice")
    private Choice choice;

    public Effect() {
        cancelled = false;
    }

    public boolean resolve(ChoiceResponse response, Player target, Turn turn, Game game) {
        if (cancelled && noValidTarget) {
            onNoValidTarget(choice, target, turn, game);
            return true;
        } else {
            return effect(response, target, turn, game);
        }
    }

    public abstract boolean effect(ChoiceResponse response, Player target, Turn turn, Game game)
            throws MissingCardException, MissingEffectException;

    void onNoValidTarget(Choice choice, Player target, Turn turn, Game game) {}

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

    public boolean hasNoValidTarget() {
        return noValidTarget;
    }

    public void setNoValidTarget(boolean noValidTarget) {
        this.noValidTarget = noValidTarget;
    }
}
