package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.CardType;

import java.util.HashSet;
import java.util.Set;

public abstract class SymmetricActionAttackCard extends ComplexActionAttackCard {

    public SymmetricActionAttackCard(Long id) {
        super(id);
        getCardTypes().add(CardType.ATTACK);
    }

    @Override
    public Set<Player> getTargets(Player player, Game game) {
        Set<Player> players = new HashSet<>();
        players.addAll(game.getPlayers().values());
        return players;
    }

}
