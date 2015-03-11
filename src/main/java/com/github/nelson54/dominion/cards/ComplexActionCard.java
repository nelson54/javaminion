package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.effects.Effect;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ComplexActionCard extends ActionCard {

    ComplexActionCard() {
        isKingdom = true;
        cardTypes.add(CardType.ACTION);
    }

    abstract Choice getChoiceForTarget(Choice parent, Player target, Game game);

    abstract Effect getEffect(Player player, Game game);

    abstract void play(Player player, Game game);

    Set<Player> getTargets(Player player, Game game) {
        Set<Player> targets = new HashSet<>();
        targets.add(player);
        return targets;
    }

    public void apply(Player player, Game game) {
        play(player, game);

        for (Player target : getTargets(player, game)) {
            addChoice(target, game);
        }
    }

    public void addChoice(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        Choice parent = findParentChoice(player);
        Choice choice = getChoiceForTarget(parent, player, game);

        choice.setSource(this);

        Effect effect;
        if (parent == null) {
            effect = getEffect(player, game);
        } else {
            effect = parent.getEffect();
        }

        choice.bind(effect);

        game.addChoice(choice);
    }

    Choice findParentChoice(Player player) {
        Turn turn = player.getCurrentTurn();

        for (Choice choice : turn.getResolvedChoices()) {
            if (this.equals(choice.getSource())) {
                return choice;
            }
        }

        return null;
    }


    Set<Player> getOtherPlayers(Player player, Game game) {
        return game.getPlayers()
                .values()
                .stream()
                .filter(p -> !p.getId().equals(player.getId()))
                .collect(Collectors.toSet());
    }

}
