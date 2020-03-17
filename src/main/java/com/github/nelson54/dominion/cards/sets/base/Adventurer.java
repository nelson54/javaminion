package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.TreasureCard;

import java.util.HashSet;
import java.util.Set;

public class Adventurer extends ActionCard {

    public Adventurer(Long id) {
        super(id);
        byte moneyCost = 6;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Adventurer");
    }

    @Override
    public void apply(Player player, Game game) {

        Set<Card> nonTreasures = new HashSet<>();
        Set<Card> treasures = new HashSet<>();

        while (isStillLooking(player, treasures)) {
            Card topCard = player.revealCard().get();
            player.getDeck().remove(topCard);
            if (topCard instanceof TreasureCard) {
                treasures.add(topCard);
            } else {
                nonTreasures.add(topCard);
            }
        }

        player.getHand().addAll(treasures);
        player.getDiscard().addAll(nonTreasures);
    }

    private boolean isStillLooking(Player player, Set<Card> treasures) {
        return treasures.size() < 2 || (player.getDeck().size() + player.getDiscard().size()) > 0;
    }
}
