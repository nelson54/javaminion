package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.cards.types.ActionCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Derek on 3/14/2015.
 */
public class AdventurerTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        ActionCard card = (ActionCard)game.giveCardToPlayer("Adventurer", player);

        int startingHandSize = player.getHand().size();
        int startingMoney = player.getCurrentTurn().getMoney();
        card.apply(player, game);

        assertEquals(2, player.getHand().size() - startingHandSize, "Playing Laboratory draws 2 cards");
        assertTrue(startingMoney < player.getCurrentTurn().getMoney(), "Gives you more money.");
    }
}
