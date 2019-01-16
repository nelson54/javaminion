package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class SymmetricActionAttackCard extends ComplexActionAttackCard {

    public SymmetricActionAttackCard(Long id) {
        super(id);
    }

    public SymmetricActionAttackCard(Long id, Player player) {
        super(id, player);
    }

    @Override
    public Set<Player> getTargets(Player player, Game game) {
        Set<Player> players = new HashSet<>();
        players.addAll(game.getPlayers().values());
        return players;
    }

}
