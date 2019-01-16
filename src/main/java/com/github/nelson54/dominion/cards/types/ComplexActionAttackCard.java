package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Derek on 3/11/2015.
 */
public abstract class ComplexActionAttackCard extends ComplexActionCard {

    public ComplexActionAttackCard(Long id) {
        super(id);
    }

    public ComplexActionAttackCard(Long id, Player player) {
        super(id, player);
    }

    @Override
    public Set<Player> getTargets(Player player, Game game) {
        return game.getPlayers().values().stream()
                .filter(otherPlayer -> !otherPlayer.getId().equals(player.getId()))
                .collect(Collectors.toSet());
    }
}
