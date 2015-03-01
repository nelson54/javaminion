package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;

import java.util.*;

import static com.github.nelson54.dominion.Phase.*;


public class Game {

    @JsonProperty
    UUID id;

    @JsonProperty
    Kingdom kingdom;

    @JsonIgnore
    Set<Player> turnOrder;

    @JsonIgnore
    List<Turn> pastTurns;

    @JsonIgnore
    Iterator<Player> turnerator;

    @JsonProperty
    Map<String, Player> players;

    @JsonProperty
    boolean gameOver;

    @JsonProperty
    Turn turn;

    public Game() {
        id = UUID.randomUUID();
        pastTurns = new ArrayList<>();
    }

    Player nextPlayer(){
        if(turnerator == null || !turnerator.hasNext()){
            turnerator = turnOrder.iterator();
        }

        if(isGameOver()){
            turn.phase = END_OF_GAME;
            gameOver = true;
            return null;
        }

        pastTurns.add(turn);
        turn = new Turn();
        turn.setGame(this);
        turn.setBuyPool(1);
        turn.setActionPool(1);
        turn.setMoneyPool(0);
        turn.setPhase(ACTION);
        turn.setPlay(new LinkedHashSet<>());
        turn.setPlayer(turnerator.next());

        return turn.getPlayer();
    }

    public Card giveCardToPlayer(String name, Player player){
        Collection<Card> cards = kingdom.getCardsByName(name);
        Optional<Card> purchasedCard;

        if(cards != null) {
            purchasedCard = cards.stream().filter(card -> card.getOwner() == null).findFirst();
            cards.remove(purchasedCard.get());
            purchasedCard.ifPresent(card -> card.setOwner(player));
            player.getDiscard().add(purchasedCard.get());
            return purchasedCard.get();
        } else {
            return null;
        }
    }

    boolean isGameOver(){
        return kingdom.getNumberOfRemainingCardsByName("Province") == 0;
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
        this.kingdom = kingdom;
    }

    Set<Player> getTurnOrder() {
        return turnOrder;
    }

    void setTurnOrder(Set<Player> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}
