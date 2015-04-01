package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Card {

    @JsonProperty
    boolean isKingdom;
    @JsonProperty
    Set<CardType> cardTypes;
    @JsonProperty
    private
    String id;
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
    int kingdomSortOrder = 0;

    Card() {
        id = UUID.randomUUID().toString();
        cardTypes = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void onEnterHand() {

    }

    public void onLeaveHand() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        return id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
