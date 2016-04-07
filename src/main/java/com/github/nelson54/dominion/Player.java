package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.VictoryCard;
import com.github.nelson54.dominion.choices.Choice;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    @JsonProperty
    String id;

    @JsonProperty
    String name;

    @JsonProperty
    Set<Card> hand;

    @JsonProperty
    Set<Card> deck;

    @JsonProperty
    Set<Card> discard;

    @JsonIgnore
    Game game;

    @JsonProperty
    Turn currentTurn;

    @JsonProperty
    Set<Choice> choices;

    public Player() {
        id = UUID.randomUUID().toString();
        hand = new HashSet<>();
        deck = new LinkedHashSet<>();
        discard = new HashSet<>();
        choices = new LinkedHashSet<>();
    }

    @JsonProperty
    public long getVictoryPoints() {
        return getAllCards()
                .values().stream()
                .filter(card -> card instanceof VictoryCard)
                .map(card -> (VictoryCard) card)
                .mapToLong(VictoryCard::getVictoryPoints)
                .sum();
    }

    @JsonIgnore
    public boolean hasActionsInHand() {
        return false;
    }

    public void resetForNextTurn(Turn turn) {
        if(turn != null){
            hand.addAll(turn.getPlay());
        }

        discardHand();
        drawHand();

        currentTurn = Turn.create(this);

    }

    public void discard(Card card){
        hand.remove(card);
        deck.remove(card);
        discard.add(card);
    }

    public void discard(Set<Card> cards){
        String list = cards.stream()
                .map(Card::getId)
                .collect(Collectors.joining(", "));

        hand.removeAll(cards);
        deck.removeAll(cards);
        discard.addAll(cards);

        game.log(getName() + " discarded: " + list);
    }

    public void putOnTopOfDeck(Card card){
        hand.remove(card);
        deck.add(card);
    }

    public void shuffle() {
        List<Card> shuffledDeck = new ArrayList<>();

        shuffledDeck.addAll(discard);
        shuffledDeck.addAll(deck);

        discard.clear();
        deck.clear();

        Collections.shuffle(shuffledDeck);

        deck.addAll(shuffledDeck);
    }

    public void drawHand() {
        drawXCards(5);
    }

    public void discardHand() {


        discard.addAll(hand);
        hand.clear();
    }

    public void drawXCards(long x) {
        List<Card> drawnCards = new ArrayList<>();

        if (deck.size() < x) {
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

    public Card revealCard() {
        if (deck.size() == 0) {
            shuffle();
        }

        return deck.stream()
                .findFirst()
                .get();
    }

    public Set<Card> revealCards(int number) {
        if (deck.size() == 0) {
            shuffle();
        }

        return deck.stream()
                .limit(number)
                .collect(Collectors.toSet());
    }

    public Map<String, Card> getAllCards() {
        Map<String, Card> allCards = new HashMap<>();

        hand.stream().forEach(card -> allCards.put(card.getId().toString(), card));
        deck.stream().forEach(card -> allCards.put(card.getId().toString(), card));
        discard.stream().forEach(card -> allCards.put(card.getId().toString(), card));

        return allCards;
    }

    public void onActionPhase(){

    }

    public void onBuyPhase(){

    }

    public void onChoice(){

    }

    public void onEndOfTurn(){

    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Turn currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return id.toString().equals(player.id.toString());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + hand.hashCode();
        result = 31 * result + deck.hashCode();
        result = 31 * result + discard.hashCode();
        result = 31 * result + choices.hashCode();
        return result;
    }
}
