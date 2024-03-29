package com.github.nelson54.dominion.cards.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.game.choices.Choice;

import java.util.HashSet;
import java.util.Set;


public class Card {

    @JsonProperty
    boolean isKingdom;

    @JsonProperty
    Set<CardType> cardTypes;

    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(as = Long.class)
    private Long id;

    @JsonIgnore
    private Player owner;

    @JsonProperty
    private String name;

    @JsonProperty
    private Cost cost;

    @JsonProperty
    int kingdomSortOrder = 0;

    public Card() {}

    public Card(Long id) {
        this.id = id;
        cardTypes = new HashSet<>();
    }



    public Card(Long id, Player owner) {
        this.id = id;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isKingdom() {
        return isKingdom;
    }

    public void setKingdom(boolean kingdom) {
        isKingdom = kingdom;
    }

    public int getKingdomSortOrder() {
        return kingdomSortOrder;
    }

    public void setKingdomSortOrder(int kingdomSortOrder) {
        this.kingdomSortOrder = kingdomSortOrder;
    }

    public boolean isType(CardType type) {
        return cardTypes.contains(type);
    }


    public void onCardPlayed(Card card) {

    }

    public void onCardTrashed(Card card) {

    }

    public void onChoice(Choice choice) {

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
