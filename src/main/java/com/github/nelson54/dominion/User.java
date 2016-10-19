package com.github.nelson54.dominion;

import java.util.HashSet;
import java.util.Set;

public final class User {
    private Set<String> games;
    private final String id;
    private String name;
    private boolean ai;

    public User(String id) {
        games = new HashSet<>();
        this.id = id;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }


    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }
}
