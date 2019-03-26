package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.exceptions.InsufficientActionsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void testIncorrectPhaseException() {

        DominionTestCase testCase = new DominionTestCase();
        Game game = testCase.game;


        Turn turn = game.getTurn();
        turn.setPhase(Phase.BUY);

        Player player = turn.getPlayer();
        ActionCard card = (ActionCard)game.giveCardToPlayer("Adventurer", player);

        assertThrows(InsufficientActionsException.class, ()-> turn.playCard(card, player, game), "Throws exception when no turns.");


    }

    @Test
    void testInsufficientActionsException() {

        DominionTestCase testCase = new DominionTestCase();
        Game game = testCase.game;


        Turn turn = game.getTurn();
        turn.setActionPool(0);
        turn.setPhase(Phase.ACTION);

        Player player = turn.getPlayer();
        ActionCard card = (ActionCard)game.giveCardToPlayer("Adventurer", player);

        assertThrows(InsufficientActionsException.class, ()-> turn.playCard(card, player, game), "Throws exception when no turns.");
    }

    @Test
    void testEquals() {

        DominionTestCase testCase = new DominionTestCase();
        Game game = testCase.game;

        Turn turn = game.getTurn();
        assertTrue(turn.equals(turn), "Equals Self");

        assertFalse(turn.equals(""), "Not equals string Self");

        assertFalse(turn.equals(null), "Not equals null");


    }
}