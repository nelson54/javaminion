package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.google.common.collect.Multimap;

import java.util.*;

import static com.github.nelson54.dominion.Phase.BUY;
import static com.github.nelson54.dominion.Phase.END_OF_GAME;


public class Game {

    @JsonProperty
    UUID id;

    @JsonProperty
    Kingdom kingdom;

    @JsonIgnore
    Set<Player> turnOrder;

    @JsonIgnore
    Map<String, Card> allCards;

    @JsonIgnore
    List<Turn> pastTurns;

    @JsonProperty
    Set<Card> trash;

    @JsonIgnore
    Iterator<Player> turnerator;

    @JsonProperty
    Map<String, Player> players;

    @JsonProperty
    boolean gameOver;

    @JsonProperty
    Turn turn;

    @JsonProperty
    private
    Set<Choice> choices;

    public Game() {
        id = UUID.randomUUID();
        pastTurns = new ArrayList<>();
        allCards = new HashMap<>();
        trash = new HashSet<>();
        choices = new HashSet<>();

    }

    Player nextPlayer(){
        if(turnerator == null || !turnerator.hasNext()){
            turnerator = turnOrder.iterator();
        }

        if(turn != null ) {
            turn.getResolvedChoices().addAll(choices);
            choices.clear();
        }

        if(isGameOver()){
            turn.phase = END_OF_GAME;
            gameOver = true;
            return null;
        }

        Player nextPlayer = turnerator.next();
        pastTurns.add(turn);
        turn = nextPlayer.getCurrentTurn();

        if(!turn.hasActionsInHand()){
            turn.setPhase(BUY);
        }

        return nextPlayer;
    }

    public Card giveCardToPlayer(String name, Player player){
        Collection<Card> cards = kingdom.getCardsByName(name);
        Optional<Card> purchasedCard;

        if(cards != null) {
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

    public Optional<Choice> getChoiceById(String id) {
        Optional<Choice> optChoice = Optional.empty();

        for (Choice choice : choices) {
            if (choice.getId().toString().equals(id)) {

                optChoice = Optional.of(choice);
                break;
            }
        }

        return optChoice;
    }

    public void addChoice(Choice choice) {
        turn.phase = Phase.WAITING_FOR_CHOICE;
        choices.add(choice);
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void trashCard(Card card){
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

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    public Set<Card> getTrash() {
        return trash;
    }
}
