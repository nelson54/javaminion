package com.github.nelson54.dominion.cards

import com.github.nelson54.dominion.GameTest
import com.github.nelson54.dominion.Player
import org.junit.Before
import org.junit.Test

/**
 * Created by Derek on 3/14/2015.
 */
class SmithyTest extends GameTest {

    @Before
    void setUp() {
        super.setUp()
    }

    @Test
    void testApply() {
        Player player = getCurrentPlayer()

        int startingHandSize = player.hand.size()

        ActionCard card = (ActionCard)game.giveCardToPlayer("Smithy", player)
        card.apply(player, game)

        assert player.hand.size()-startingHandSize == 3, "Playing smithy draws 3 cards"
    }
}
