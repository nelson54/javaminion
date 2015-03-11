package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.VictoryCard;

import java.util.*;

import static com.github.nelson54.dominion.Phase.ACTION;

public class Player {
    @JsonProperty
    private
    UUID id;

    @JsonProperty
    private
    Set<Card> hand;

    @JsonProperty
    private
    Set<Card> deck;

    @JsonProperty
    private
    Set<Card> discard;

    @JsonIgnore
    private
    Game game;

    @JsonProperty
    private
    Turn currentTurn;

    Player() {
        id = UUID.randomUUID();
        hand = new HashSet<>();
        deck = new LinkedHashSet<>();
        discard = new HashSet<>();
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

        discardHand();
        drawHand();

        currentTurn = new Turn();
        currentTurn.setPlayerId(id.toString());
        currentTurn.setGame(game);
        currentTurn.setBuyPool(1);
        currentTurn.setActionPool(1);
        currentTurn.setMoneyPool(0);
        currentTurn.setPhase(ACTION);
        currentTurn.setPlay(new LinkedHashMap<>());
        currentTurn.setPlayer(this);

    }

    public void discard(Set<Card> cards){
        hand.removeAll(cards);
        discard.addAll(cards);
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

    public Map<String, Card> getAllCards() {
        Map<String, Card> allCards = new HashMap<>();

        hand.stream().forEach(card -> allCards.put(card.getId().toString(), card));
        deck.stream().forEach(card -> allCards.put(card.getId().toString(), card));
        discard.stream().forEach(card -> allCards.put(card.getId().toString(), card));

        return allCards;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return id.toString().equals(player.id.toString());

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    void meh(Optional<String> str) {

        str.ifPresent(String::trim);

    }
}
