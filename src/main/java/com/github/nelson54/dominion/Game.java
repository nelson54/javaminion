package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Kingdom;
import com.github.nelson54.dominion.cards.Kingdom;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.github.nelson54.dominion.Phase.*;

/**
 * Created by dnelson on 2/26/2015.
 */
public class Game {
    @JsonProperty
    UUID id;

    @JsonProperty
    Kingdom kingdom;

    @JsonIgnore
    Set<Player> turnOrder;

    @JsonIgnore
    Iterator<Player> turnerator;

    @JsonProperty
    Map<String, Player> players;

    @JsonProperty
    Player active;

    @JsonProperty
    Phase phase;

    public void endPhase(){
        switch(phase){
            case BUY:
                phase = ACTION;
                active.resetForTurn();
                nextPlayer();
                break;
            case ACTION:
            default:
                phase = BUY;
                break;
        }
    }

    Player nextPlayer(){
        if(turnerator == null || !turnerator.hasNext()){
            turnerator = turnOrder.iterator();
        }

        if(isGameOver()){
            phase = END;
            return null;
        }

        return active = turnerator.next();
    }

    boolean isGameOver(){
        return kingdom.getNumberOfRemainingCardsByName("Province") == 0;
    }

    public Game() {
        id = UUID.randomUUID();
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

    public Player getActive() {
        return active;
    }

    public void setActive(Player active) {
        this.active = active;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    Set<Player> getTurnOrder() {
        return turnOrder;
    }

    void setTurnOrder(Set<Player> turnOrder) {
        this.turnOrder = turnOrder;
    }
}
