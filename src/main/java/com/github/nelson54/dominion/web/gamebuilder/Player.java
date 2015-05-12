package com.github.nelson54.dominion.web.gamebuilder;

/**
 * Created by Derek on 4/18/2015.
 */
public class Player {
    String id;
    String name;
    boolean ai;

    public Player(String id) {
        this.id = id;
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

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }
}
