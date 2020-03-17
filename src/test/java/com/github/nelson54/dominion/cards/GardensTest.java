package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.cards.types.VictoryCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Derek on 3/14/2015.
 */
public class GardensTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        VictoryCard card = null;
        for(int i=0; i<10; i++) {
            card = (VictoryCard)game.giveCardToPlayer("Gardens", player);
        }

        assertEquals((byte)2, card.getVictoryPoints(), "With 20 cards each garden is worth 2 points.");

        assertEquals(
                23,
                player.getVictoryPoints(),
                "With 10 additional gardens in the starting deck the player has 23 points");
    }
}
