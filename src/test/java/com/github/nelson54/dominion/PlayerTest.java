package com.github.nelson54.dominion;


import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by Derek on 3/14/2015.
 */
class PlayerTest extends DominionTestCase {


    @Test
    void testGetVictoryPoints() {
        assertTrue(player.getVictoryPoints() == 3, "Players start the game with 3 estates");
        game.giveCardToPlayer("Province", player);
        assertTrue(player.getVictoryPoints() == 9, "Adding victory cards effects the result");
    }

}
