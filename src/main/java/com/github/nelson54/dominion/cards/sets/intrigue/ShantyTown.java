package com.github.nelson54.dominion.cards.sets.intrigue;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;

import java.util.Set;

public class ShantyTown extends ActionCard {

    @Override
    public void apply(Player player, Game game) {
        game.revealCardsFromHand(player, player.getHand());

        if(Cards.ofType(player.getHand(), CardType.ACTION).size() >= 2) {
            player.drawXCards(2);
        }
    }
}
