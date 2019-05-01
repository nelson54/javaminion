package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.base.CurseCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.VictoryCard;
import com.github.nelson54.dominion.choices.Choice;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    @JsonProperty
    private Account account;

    @JsonProperty
    private LinkedHashSet<Card> hand;

    @JsonProperty
    private LinkedHashSet<Card> deck;

    @JsonProperty
    private LinkedHashSet<Card> discard;

    @JsonIgnore
    private Game game;

    @JsonProperty
    private Turn currentTurn;

    @JsonProperty
    private Deque<Choice> choices;

    public Player(Account account) {
        this.account = account;
        hand = new LinkedHashSet<>();
        deck = new LinkedHashSet<>();
        discard = new LinkedHashSet<>();
        choices = new LinkedList<>();
    }

    @JsonProperty("id")
    public Long getId() {
        return account.getId();
    }

    @JsonProperty("name")
    public String getName() {
        return account.getUser().getUsername();
    }

    public byte order;

    @JsonProperty
    public long getVictoryPoints() {
        Long victoryCardPoints = getAllCards()
                .values().stream()
                .filter(card -> card.isType(CardType.VICTORY))
                .map(card -> (VictoryCard) card)
                .mapToLong(VictoryCard::getVictoryPoints)
                .sum();

        Long curseCardPoints = getAllCards()
                .values().stream()
                .filter(card -> card.isType(CardType.CURSE))
                .map(card -> (CurseCard) card)
                .mapToLong(CurseCard::getVictoryPoints)
                .sum();

        return victoryCardPoints + curseCardPoints;
    }

    @JsonIgnore
    public boolean hasActionsInHand() {
        return false;
    }

    protected void resetForNextTurn(Turn turn) {
        if (turn != null) {
            hand.addAll(turn.getPlay());
        }

        discardHand();
        drawHand();

        currentTurn = Turn.create(this);

    }

    public void discard(Card card) {
        hand.remove(card);
        deck.remove(card);
        discard.add(card);
    }

    public void discard(Set<Card> cards) {
        String list = cards.stream()
                .map(Card::getName)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        game.log(getName() + " discarded: " + list);

        hand.removeAll(cards);
        deck.removeAll(cards);
        discard.addAll(cards);
    }

    public void putOnTopOfDeck(Card card) {
        hand.remove(card);
        deck.add(card);
    }

    public void shuffle() {
        List<Card> shuffledDeck = new ArrayList<>();

        shuffledDeck.addAll(discard);
        shuffledDeck.addAll(deck);

        discard.clear();
        deck.clear();

        Collections.shuffle(shuffledDeck, this.game.seed.random());

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

    public Optional<Card> revealCard() {
        if (deck.size() == 0) {
            shuffle();
        }

        return deck.stream()
                .findFirst();
    }

    public Set<Card> revealCards(int number) {
        if (deck.size() == 0) {
            shuffle();
        }

        return deck.stream()
                .limit(number)
                .collect(Collectors.toSet());
    }

    public void revealCardFromHand(Card card) {
        game.revealCard(this, card);
    }

    public void revealHand() {
        game.revealCardsFromHand(this, this.hand);
    }

    @JsonIgnore
    public Map<Long, Card> getAllCards() {
        Map<Long, Card> allCards = new HashMap<>();

        hand.forEach(card -> allCards.put(card.getId(), card));
        deck.forEach(card -> allCards.put(card.getId(), card));
        discard.forEach(card -> allCards.put(card.getId(), card));

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

    public Set<Card> getHand() {
        return hand;
    }

    public void setHand(LinkedHashSet<Card> hand) {
        this.hand = hand;
    }

    public LinkedHashSet<Card> getDeck() {
        return deck;
    }

    public void setDeck(LinkedHashSet<Card> deck) {
        this.deck = deck;
    }

    public LinkedHashSet<Card> getDiscard() {
        return discard;
    }

    public void setDiscard(LinkedHashSet<Card> discard) {
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

    public Deque<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Deque<Choice> choices) {
        this.choices = choices;
    }

    public Account getAccount() {
        return account;
    }

    void addChoice(Choice choice) {
        choices.addFirst(choice);
        this.onChoice();
    }

    public void setOrder(byte order) {
        this.order = order;
    }

    public byte getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return getId().equals(player.getId());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (hand != null ? hand.hashCode() : 0);
        result = 31 * result + (deck != null ? deck.hashCode() : 0);
        result = 31 * result + (discard != null ? discard.hashCode() : 0);
        result = 31 * result + (currentTurn != null ? currentTurn.hashCode() : 0);
        result = 31 * result + (choices != null ? choices.hashCode() : 0);
        return result;
    }
}
