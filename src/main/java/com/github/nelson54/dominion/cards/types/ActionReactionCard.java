package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.Effect;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class ActionReactionCard extends ActionCard {

    public ActionReactionCard() {
        super();
        cardTypes.add(CardType.REACTION);
    }

    public ActionReactionCard(String id, Player player) {
        super(id, player);
    }

    public abstract void apply(Player player, Game game);

    public abstract void react(Effect effect);

    public Set<Player> getOtherPlayers(Player player, Game game) {
        Map<Long, Player> players = game.getPlayers();

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
