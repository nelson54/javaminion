package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.events.GameEvent;
import com.github.nelson54.dominion.events.GameEventFactory;
import com.google.common.collect.Multimap;

import java.util.*;

import static com.github.nelson54.dominion.Phase.END_OF_GAME;


public class Game {

    @JsonProperty
    private
    UUID id;

    @JsonProperty
    private
    Kingdom kingdom;

    @JsonIgnore
    private
    Set<Player> turnOrder;

    @JsonIgnore
    private
    Map<String, Card> allCards;

    @JsonIgnore
    private
    List<Turn> pastTurns;

    @JsonProperty
    private
    Set<Card> trash;

    @JsonIgnore
    private
    Iterator<Player> turnerator;

    @JsonProperty
    private
    Map<String, Player> players;

    @JsonProperty
    private
    boolean gameOver;

    @JsonProperty
    private
    Turn turn;

    @JsonIgnore
    private
    GameEventFactory gameEventFactory;

    public Game() {
        id = UUID.randomUUID();
        pastTurns = new ArrayList<>();
        allCards = new HashMap<>();
        trash = new HashSet<>();
    }

    public GameEvent trigger(GameEvent gameEvent) {
        return gameEvent;
    }

    Player nextPlayer() {
        if (turnerator == null || !turnerator.hasNext()) {
            turnerator = turnOrder.iterator();
        }

        if (isGameOver()) {
            turn.phase = END_OF_GAME;
            gameOver = true;
            return null;
        }

        Player nextPlayer = turnerator.next();
        pastTurns.add(turn);
        turn = nextPlayer.getCurrentTurn();
        return nextPlayer;
    }

    public Card giveCardToPlayer(String name, Player player) {
        Collection<Card> cards = kingdom.getCardsByName(name);
        Optional<Card> purchasedCard;

        if (cards != null) {
            purchasedCard = cards.stream().filter(card -> card.getOwner() == null).findFirst();
            cards.remove(purchasedCard.get());
            purchasedCard.ifPresent(card -> card.setOwner(player));
            player.getDiscard().add(purchasedCard.get());
            purchasedCard.ifPresent(card -> card.setOwner(player));
            return purchasedCard.get();
        } else {
            return null;
        }
    }

    boolean isGameOver() {
        return kingdom.getNumberOfRemainingCardsByName("Province") == 0;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        Multimap<String, Card> market = kingdom.getCardMarket();
        Set<Card> allCards = new HashSet<>();
        market.keySet().stream()
                .map(market::get)
                .forEach(allCards::addAll);

        allCards.stream()
                .forEach(card -> this.allCards.put(card.getId().toString(), card));

        this.kingdom = kingdom;
    }

    Set<Player> getTurnOrder() {
        return turnOrder;
    }

    void setTurnOrder(Set<Player> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void trashCard(Card card) {
        Player player = card.getOwner();

        player.getDiscard().remove(card);
        player.getDeck().remove(card);
        player.getHand().remove(card);

        card.setOwner(null);

        trash.add(card);
    }

    public Map<String, Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(Map<String, Card> allCards) {
        this.allCards = allCards;
    }

    public GameEventFactory getGameEventFactory() {
        return gameEventFactory;
    }

    public void setGameEventFactory(GameEventFactory gameEventFactory) {
        this.gameEventFactory = gameEventFactory;
    }
}
