package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.nelson54.dominion.Phase.*;


public class Game {

    @JsonProperty
    List<String> logs;

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

    public Game() {
        id = UUID.randomUUID();
        pastTurns = new ArrayList<>();
        allCards = new HashMap<>();
        trash = new HashSet<>();
    }

    Player nextPlayer() {
        if (turnerator == null || !turnerator.hasNext()) {
            turnerator = turnOrder.iterator();
        }

        if (turn != null) {
            clearAllChoices();
        }

        if (isGameOver()) {
            turn.phase = END_OF_GAME;
            gameOver = true;
            return null;
        }

        Player nextPlayer = turnerator.next();
        pastTurns.add(turn);
        turn = nextPlayer.getCurrentTurn();

        if (!turn.hasActionsInHand()) {
            turn.setPhase(BUY);
            nextPlayer.onBuyPhase();
        } else {
            turn.setPhase(ACTION);
            nextPlayer.onActionPhase();
        }

        return nextPlayer;
    }

    void clearAllChoices() {
        for (Player player : players.values()) {
            turn.getResolvedChoices().addAll(player.getChoices());
            player.getChoices().clear();
        }
    }

    boolean isGameOver() {
        return kingdom.getNumberOfRemainingCardsByName("Province") == 0;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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

    public Optional<Choice> getChoiceById(String id) {
        Optional<Choice> optChoice = Optional.empty();

        for (Choice choice : getChoices()) {
            if (choice.getId().toString().equals(id)) {

                optChoice = Optional.of(choice);
                break;
            }
        }

        return optChoice;
    }

    public Set<Choice> getChoices() {
        return players.values().stream()
                .flatMap(p -> p.getChoices().stream())
                .collect(Collectors.toSet());
    }

    public void addChoice(Choice choice) {
        turn.phase = Phase.WAITING_FOR_CHOICE;
        choice.getTarget().getChoices().add(choice);
        choice.getTarget().onChoice();
    }

    public void endGame() {
        players.values().stream()
                .map(Player::getCurrentTurn)
                .forEach(turn -> turn.setPhase(END_OF_GAME));
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
        player.getCurrentTurn().getPlay().remove(card);
        player.getDiscard().remove(card);
        player.getDeck().remove(card);
        player.getHand().remove(card);

        card.setOwner(null);

        trash.add(card);
    }

    public void log(String string){
        logs.add(string);
    }

    public Map<String, Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(Map<String, Card> allCards) {
        this.allCards = allCards;
    }

    public Set<Card> getTrash() {
        return trash;
    }

    @Override
    public int hashCode() {
        int result = (id.hashCode() ^ (id.hashCode() >>> 0));
        result = 31 * result + kingdom.hashCode();
        result = 31 * result + turnOrder.hashCode();
        result = 31 * result + allCards.hashCode();
        result = 31 * result + pastTurns.hashCode();
        result = 31 * result + trash.hashCode();
        result = 31 * result + turnerator.hashCode();
        result = 31 * result + players.hashCode();
        result = 31 * result + (gameOver ? 1 : 0);
        result = 31 * result + turn.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (gameOver != game.gameOver) return false;
        if (!allCards.equals(game.allCards)) return false;
        if (!id.equals(game.id)) return false;
        if (!kingdom.equals(game.kingdom)) return false;
        if (!pastTurns.equals(game.pastTurns)) return false;
        if (!players.equals(game.players)) return false;
        if (!trash.equals(game.trash)) return false;
        if (!turn.equals(game.turn)) return false;
        if (!turnOrder.equals(game.turnOrder)) return false;
        if (!turnerator.equals(game.turnerator)) return false;

        return true;
    }
}
