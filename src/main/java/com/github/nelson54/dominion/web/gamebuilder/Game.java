package com.github.nelson54.dominion.web.gamebuilder;

import java.util.List;

/**
 * Created by Derek on 4/18/2015.
 */
public class Game {

    String cardSet;
    int count;
    List<Player> players;

    public Game() {
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
