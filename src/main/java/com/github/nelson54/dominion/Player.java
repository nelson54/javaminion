package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.TreasureCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.TreasureCard;

import java.util.*;

public class Player {
    @JsonProperty
    UUID id;

    @JsonProperty
    Set<Card> play;

    @JsonProperty
    Set<Card> hand;

    @JsonProperty
    Set<Card> deck;

    @JsonProperty
    Set<Card> discard;

    @JsonProperty
    long actions = 1;

    @JsonProperty
    long buys = 1;

    @JsonIgnore
    long moneys = 0;

    Player(){
        id = UUID.randomUUID();
        play = new HashSet<>();
        hand = new HashSet<>();
        deck = new LinkedHashSet<>();
        discard = new HashSet<>();
    }

    @JsonIgnore
    public boolean hasActionsInHand(){
        return false;
    }

    public void resetForTurn(){
        buys = 1;
        actions = 1;
        moneys = 0;

        hand.addAll(play);
        play.clear();

        discardHand();
        drawHand();

    }

    @JsonProperty("money")
    public long getMoney() {
        return hand.stream()
                .filter(card -> card instanceof TreasureCard)
                .map(card -> (TreasureCard)card)
                .mapToLong(card -> (int)card.getMoneyValue(this))
                .sum() + moneys;
    }

    public void spendMoney(long money) {
        moneys -= money;
    }

    public void shuffle(){
        List<Card> shuffledDeck = new ArrayList<>();

        shuffledDeck.addAll(discard);
        shuffledDeck.addAll(deck);

        discard.clear();
        deck.clear();

        Collections.shuffle(shuffledDeck);

        deck.addAll(shuffledDeck);
    }

    public void drawHand(){
        drawXCards(5);
    }

    public void discardHand(){
        discard.addAll(hand);
        hand.clear();
    }

    public long addToMoneyPool(long money){
        return moneys += money;
    }

    public void drawXCards(long x){
        List<Card> drawnCards = new ArrayList<>();

        if(deck.size() < x){
            drawnCards.addAll(deck);
            deck.clear();
            x -= drawnCards.size();
            shuffle();
        }

        deck.stream()
                .limit(x)
                .forEachOrdered(drawnCards::add);

        hand.addAll(drawnCards);
        deck.removeAll(drawnCards);
    }

    public UUID getId() {
        return id;
    }

    void setId(UUID id) {
        this.id = id;
    }



    public Set<Card> getHand() {
        return hand;
    }

    public void setHand(Set<Card> hand) {
        this.hand = hand;
    }

    public Set<Card> getDeck() {
        return deck;
    }

    public void setDeck(Set<Card> deck) {
        this.deck = deck;
    }

    public Set<Card> getDiscard() {
        return discard;
    }

    public void setDiscard(Set<Card> discard) {
        this.discard = discard;
    }


    public long getActions() {
        return actions;
    }

    public void setActions(long actions) {
        this.actions = actions;
    }

    public long getBuys() {
        return buys;
    }

    public void setBuys(long buys) {
        this.buys = buys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (!id.toString().equals(player.id.toString())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
