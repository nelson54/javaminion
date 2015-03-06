package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import java.util.Set;

public abstract class ActionAttackCard extends ActionCard {

    ActionAttackCard() {
        super();
        cardTypes.add(CardType.ATTACK);
    }

    public void apply(Player player, Game game) {
        Set<Player> others = getOtherPlayers(player, game);
        bonus(player, game);
        others.stream().forEach(other -> attack(other, game));
    }

    protected abstract void attack(Player player, Game game);

    protected abstract void bonus(Player player, Game game);
}
