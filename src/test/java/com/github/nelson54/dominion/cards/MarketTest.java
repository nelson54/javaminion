package com.github.nelson54.dominion.cards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Derek on 3/14/2015.
 */
public class MarketTest extends DominionTestCase {

    @Test
    public void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Market", player);
        int startingHandSize = player.getHand().size();
        long startingMoney = player.getCurrentTurn().getMoneyPool();
        card.apply(player, game);
        assertTrue(player.getHand().size() == startingHandSize+1, "Playing should draw a card");
        assertTrue(player.getCurrentTurn().getMoneyPool() >= startingMoney+1, "Should have an extra money");
        assertTrue(player.getCurrentTurn().getBuyPool() == 2, "Should have an extra buy");
        assertTrue(player.getCurrentTurn().getActionPool() == 2, "Should have an extra action");
    }
}
