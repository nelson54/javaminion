package com.github.nelson54.dominion.cards

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Player
import org.junit.Before
import org.junit.Test

/**
 * Created by Derek on 3/14/2015.
 */
class CouncilRoomTest extends DominionTestCase {

    @Before
    void setUp() {
        super.setUp()
    }

    @Test
    void testApply() {
        Player player = getCurrentPlayer()
        ActionCard councilRoom = (ActionCard)game.giveCardToPlayer("Council Room", player)

        councilRoom.apply(player, game)

        assert player.hand.size() == 9, 'player draws 4 cards'
    }
}
