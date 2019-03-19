package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class ActionAttackCard extends ActionCard {

    public ActionAttackCard(Long id) {
        super(id);
        cardTypes.add(CardType.ATTACK);
    }

    public ActionAttackCard(Long id, Player player) {
        super(id, player);
    }

    public void apply(Player player, Game game) {
        Set<Player> others = getOtherPlayers(player, game).stream()
                .filter(p -> p.getHand().stream().anyMatch(c -> !c.getName().equals("Moat")))
                .collect(Collectors.toSet());


        bonus(player, game);
        others.stream().forEach(other -> attack(other, game));
    }

    protected abstract void attack(Player player, Game game);

    protected abstract void bonus(Player player, Game game);
}
