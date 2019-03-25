package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Derek on 3/14/2015.
 */
public class CurseTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {

        long startingScore = player.getVictoryPoints();

        assertEquals((long)3, startingScore, "Player's start with 3 points.");

        game.giveCardToPlayer("Curse", player);

        assertEquals((long)2, player.getVictoryPoints(), "Having a curse reduces your score by 1.");
    }
}
