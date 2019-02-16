package com.github.nelson54.dominion;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by Derek on 3/14/2015.
 */
class PlayerTest extends DominionTestCase {


    @Test
    void testGetVictoryPoints() {
        assertEquals(3, player.getVictoryPoints(), "Players start the game with 3 estates");
        game.giveCardToPlayer("Province", player);
        assertEquals(9, player.getVictoryPoints(), "Adding victory cards effects the result");
    }

}
