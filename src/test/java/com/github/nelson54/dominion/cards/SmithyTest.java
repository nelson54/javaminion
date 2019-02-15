package com.github.nelson54.dominion.cards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Derek on 3/14/2015.
 */
public class SmithyTest extends DominionTestCase {

    @Test
    public void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Smithy", player);
        int startingHandSize = player.getHand().size();
        card.apply(player, game);
        assertTrue(player.getHand().size()-startingHandSize == 3, "Playing smithy draws 3 cards");
    }
}
