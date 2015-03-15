package com.github.nelson54.dominion.cards

import com.github.nelson54.dominion.DominionTestCase
import org.junit.Before
import org.junit.Test

/**
 * Created by Derek on 3/14/2015.
 */
class SmithyTest extends DominionTestCase {

    @Before
    void setUp() {
        super.setUp()
    }

    @Test
    void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Smithy", player)

        int startingHandSize = player.hand.size()
        card.apply(player, game)

        assert player.hand.size()-startingHandSize == 3, "Playing smithy draws 3 cards"
    }
}
