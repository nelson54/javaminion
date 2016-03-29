package com.github.nelson54.dominion.web.gamebuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Derek on 4/18/2015.
 */
public class Game {

    String id;
    String cardSet;
    int count;
    Set<Player> players;

    public Game() {
        setId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int numberOfAiPlayers(){
        return (int) players.stream().filter(Player::isAi).count();
    }

    public int numberOfHumanPlayers(){
        return 1 + players.size() - numberOfAiPlayers();
    }

    public Optional<Player> findUnsetHumanPlayer(){
        return players.stream()
                .filter(p -> p.getId() != null && !p.isAi())
                .findFirst();
    }

    public Boolean hasRemainingPlayers(){
        return findUnsetHumanPlayer().isPresent();
    }
}
