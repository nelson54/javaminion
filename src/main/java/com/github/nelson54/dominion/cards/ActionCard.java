package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ActionCard extends Card {

    ActionCard(){
        cardTypes.add(CardType.ACTION);
    }

    public abstract void apply(Player player, Game game);

    Set<Player> getOtherPlayers(Player player, Game game){
        Map<String, Player> players = game.getPlayers();

        return game.getPlayers()
                .keySet()
                .stream()
                .map(players::get)
                .filter(p -> !p.getId().equals(player.getId()))
                .collect(Collectors.toSet());
    }

}
