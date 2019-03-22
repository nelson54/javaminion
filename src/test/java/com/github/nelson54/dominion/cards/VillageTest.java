package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.cards.types.ActionCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Derek on 3/14/2015.
 */
public class VillageTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Village", player);

        int startingHandSize = player.getHand().size();
        long startingActionPool = player.getCurrentTurn().getActionPool();

        card.apply(player, game);

        assertEquals(1, player.getHand().size() - startingHandSize, "Playing Village draws 2 cards");
        assertEquals(2, player.getCurrentTurn().getActionPool() - startingActionPool, "Playing Village gives an additional action");
    }
}
