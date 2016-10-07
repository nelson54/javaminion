package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.effects.Effect;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class ActionReactionCard extends ActionCard {

    ActionReactionCard() {
        super();
        cardTypes.add(CardType.REACTION);
    }

    public abstract void apply(Player player, Game game);

    public abstract void react(Effect effect);

    public Set<Player> getOtherPlayers(Player player, Game game) {
        Map<String, Player> players = game.getPlayers();

        return game.getPlayers()
                .keySet()
                .stream()
                .map(players::get)
                .filter(p -> !p.getId().equals(player.getId()))
                .collect(Collectors.toSet());

    }

    public abstract void onCardPlayed(Card card);

    public abstract void onCardTrashed(Card card);

    public abstract void onChoice(Choice choice);



}
