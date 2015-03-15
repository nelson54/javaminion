package com.github.nelson54.dominion.cards

import com.github.nelson54.dominion.DominionTestCase
import org.junit.Before
import org.junit.Test

/**
 * Created by Derek on 3/14/2015.
 */
class MarketTest extends DominionTestCase {

    @Before
    void setUp() {
        super.setUp()

    }

    @Test
    void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Market", player)

        int startingHandSize = player.hand.size()
        int startingMoney = player.currentTurn.getMoneyPool()
        card.apply(player, game)

        assert player.hand.size() == startingHandSize+1, "Playing should draw a card"
        assert player.currentTurn.getMoneyPool() >= startingMoney+1, "Should have an extra money"
        assert player.currentTurn.getBuyPool() == 2, "Should have an extra buy"
        assert player.currentTurn.getActionPool() == 2, "Should have an extra action"
    }
}
