package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Derek on 3/17/2015.
 */
public abstract class SymmetricActionAttackCard extends ComplexActionAttackCard {

    @Override
    Set<Player> getTargets(Player player, Game game) {
        Set<Player> players = new HashSet<>();
        players.addAll(game.getPlayers().values());
        return players;
    }

}
