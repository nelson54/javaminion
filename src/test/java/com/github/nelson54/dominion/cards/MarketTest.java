package com.github.nelson54.dominion.cards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Derek on 3/14/2015.
 */
public class MarketTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        ActionCard card = (ActionCard)this.game.giveCardToPlayer("Market", this.player);
        int startingHandSize = player.getHand().size();
        long startingMoney = player.getCurrentTurn().getMoneyPool();
        card.apply(player, game);
        assertEquals(player.getHand().size(), startingHandSize + 1, "Playing should draw a card");
        assertTrue(player.getCurrentTurn().getMoneyPool() >= startingMoney+1, "Should have an extra money");
        assertEquals(2, player.getCurrentTurn().getBuyPool(), "Should have an extra buy");
        assertEquals(2, player.getCurrentTurn().getActionPool(), "Should have an extra action");
    }
}
