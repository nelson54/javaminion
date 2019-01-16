package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class ActionCard extends Card {

    public ActionCard(Long id) {
        super(id);
        isKingdom = true;
        cardTypes.add(CardType.ACTION);
    }

    public ActionCard(Long id, Player player) {
        super(id, player);
    }

    public abstract void apply(Player player, Game game);

    Set<Player> getOtherPlayers(Player player, Game game) {
        return game.getPlayers()
                .values()
                .stream()
                .filter(p -> !p.getId().equals(player.getId()))
                .collect(Collectors.toSet());
    }

}
