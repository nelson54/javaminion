package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.effects.Effect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Card {

    @JsonProperty
    UUID id;

    @JsonProperty
    @JsonBackReference
    Player owner;

    @JsonProperty
    String name;

    @JsonProperty
    Cost cost;

    @JsonProperty
    Set<CardType> cardTypes;

    @JsonIgnore
    Set<Effect> effects;

    public Card() {
        id = UUID.randomUUID();
        effects = new HashSet<Effect>();
        cardTypes = new HashSet<CardType>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Set<CardType> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(Set<CardType> cardTypes) {
        this.cardTypes = cardTypes;
    }

    public Set<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Set<Effect> effects) {
        this.effects = effects;
    }
}
