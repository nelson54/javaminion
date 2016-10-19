package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.cards.GameCardSet;

import java.util.HashSet;
import java.util.Set;

public class Match {
    private byte playerCount;
    private Set<User> users;
    private GameCardSet cards;

    public Match(byte playerCount, GameCardSet cards) {
        this.users = new HashSet<>();
        this.playerCount = playerCount;
    }

    public Set<User> getUsers() {
        return users;
    }

    public GameCardSet getCards() {
        return cards;
    }

    public void addUser (User user) {
        users.add(user);
    }

    public byte getPlayerCount() {
        return playerCount;
    }

    public boolean isReady() {
        return users.size() == playerCount;
    }
}
