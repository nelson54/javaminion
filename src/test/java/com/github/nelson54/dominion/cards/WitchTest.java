package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.cards.types.ActionAttackCard;
import com.github.nelson54.dominion.cards.types.ActionCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Derek on 3/14/2015.
 */
public class WitchTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        ActionAttackCard card = (ActionAttackCard)game.giveCardToPlayer("Witch", player);
        int startingHandSize = player.getHand().size();
        card.apply(player, game);
        assertEquals(2, player.getHand().size() - startingHandSize, "Playing Witch draws 2 cards");

        long numberOfCurses = getNextPlayer().getAllCards()
                .values()
                .stream()
                .map((c)-> card.getName())
                .filter((name)-> name.equals("Curse"))
                .count();

        assertEquals((long)1, numberOfCurses, "Playing Witch gives other players one Curse");
    }
}
