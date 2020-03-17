package com.github.nelson54.dominion.game;


import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class GameTest extends DominionTestCase {

    @Test
    void testNextPlayer() {
        game.nextPlayer();
        game.nextPlayer();

        Turn turn = game.getTurn();
        player = getCurrentPlayer();

        assertTrue(game.getTurnerator().hasNext(), "There should be a next player");
        assertTrue(player.getAllCards().size() == 10, "Start game with 10 cards total");
        assertTrue(player.getHand().size() == 5, "Start with 5 cards in hand");
        assertTrue(player.getDeck().size() == 5, "Start with 5 cards in deck");

        kingdom.getCardMarket().clear();

        game.nextPlayer();
        assert turn.getPhase() == Phase.END_OF_GAME;
    }

    @Test
    void testIsGameOver() {
        Kingdom kingdom = game.getKingdom();

        kingdom.getCardMarket().clear();

        assertTrue(game.isGameOver(), "Game ends if cards are gone.");


    }

}
