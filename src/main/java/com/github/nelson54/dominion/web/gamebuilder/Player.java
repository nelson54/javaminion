package com.github.nelson54.dominion.web.gamebuilder;

/**
 * Created by dnelson on 11/9/2016.
 */
public abstract class Player {

    public Player(String id) {
    }

    public abstract String getId();
    public abstract String getName();
    public abstract void setName(String name);
    public abstract boolean isAi();
}
