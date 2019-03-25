package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerTest extends DominionTestCase {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        player.getHand().clear();
        player.getDeck().clear();
        player.getDiscard().clear();

        ActionCard card = (ActionCard)game.giveCardToPlayer("Adventurer", player);

        game.giveCardToPlayer("Silver", player);
        game.giveCardToPlayer("Silver", player);

        int startingHandSize = player.getHand().size();
        int startingMoney = player.getCurrentTurn().getMoney();

        assertTimeoutPreemptively(Duration.ofMillis(500), () -> card.apply(player, game));

        assertEquals(2, player.getHand().size() - startingHandSize, "Playing Adventurer draws 2 cards");
        assertTrue(startingMoney < player.getCurrentTurn().getMoney(), "Gives you more money.");
    }
}
