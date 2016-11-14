package com.github.nelson54.dominion.web.gamebuilder;

import com.github.nelson54.dominion.Player;

import java.util.Set;

public abstract class Game {
    public abstract String getId();
    public abstract Set<com.github.nelson54.dominion.web.gamebuilder.Player> getPlayers();
    public abstract int numberOfHumanPlayers();
}
