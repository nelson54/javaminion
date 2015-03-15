package com.github.nelson54.dominion

import org.junit.Before
import org.junit.Test

/**
 * Created by Derek on 3/14/2015.
 */
class PlayerTest extends DominionTestCase {

    @Before
    void setUp() {
        super.setUp()
    }

    @Test
    void testGetVictoryPoints() {

        assert player.getVictoryPoints() == 3, 'Players start the game with 3 estates'

        game.giveCardToPlayer("Province", player)

        assert player.getVictoryPoints() == 9, 'Adding victory cards effects the result'
    }

}
