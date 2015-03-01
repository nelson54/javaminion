package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Card {

    @JsonProperty
    private
    UUID id;

    @JsonIgnore
    private
    Player owner;

    @JsonProperty
    private
    String name;

    @JsonProperty
    private
    Cost cost;

    @JsonProperty
    Set<CardType> cardTypes;

    public Card() {
        id = UUID.randomUUID();
        cardTypes = new HashSet<>();
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

    void setName(String name) {
        this.name = name;
    }

    public Cost getCost() {
        return cost;
    }

    void setCost(Cost cost) {
        this.cost = cost;
    }

    public Set<CardType> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(Set<CardType> cardTypes) {
        this.cardTypes = cardTypes;
    }
}
