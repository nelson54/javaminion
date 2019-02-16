package com.github.nelson54.dominion.cards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Derek on 3/14/2015.
 */
public class SmithyTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Smithy", player);
        int startingHandSize = player.getHand().size();
        card.apply(player, game);
        assertEquals(3, player.getHand().size() - startingHandSize, "Playing smithy draws 3 cards");
    }
}
