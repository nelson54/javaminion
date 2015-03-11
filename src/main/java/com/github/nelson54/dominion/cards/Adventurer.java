package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import java.util.HashSet;
import java.util.Set;

public class Adventurer extends ActionCard {

    public Adventurer() {
        super();
        byte moneyCost = 6;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Adventurer");
    }

    @Override
    public void apply(Player player, Game game) {
        Set<Card> treasures = new HashSet<>();

        while (isDoneLooking(player, treasures)) {
            Card topCard = player.revealCard();

            if (topCard instanceof TreasureCard) {
                treasures.add(topCard);
            } else {
                player.getDeck().remove(topCard);
                player.getDiscard().add(topCard);
            }
        }

        if (treasures.size() > 0) {
            player.getHand().addAll(treasures);
        }
    }

    private boolean isDoneLooking(Player player, Set<Card> treasures) {
        return !hasCardsRemaining(player) || treasures.size() == 2;
    }

    private boolean hasCardsRemaining(Player player) {
        return player.getDeck().size() == 0 && player.getDiscard().size() == 0;
    }
}