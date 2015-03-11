package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.effects.Effect;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Derek on 3/11/2015.
 */
public abstract class ComplexActionAttackCard extends ComplexActionCard {

    Set<Player> getTargets(Player player, Game game) {
        return game.getPlayers().values().stream()
                .filter(otherPlayer -> !otherPlayer.getId().equals(player.getId()))
                .collect(Collectors.toSet());
    }
}
