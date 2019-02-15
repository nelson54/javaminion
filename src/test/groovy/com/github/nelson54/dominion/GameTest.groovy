package com.github.nelson54.dominion

import org.junit.Before
import org.junit.Test


class GameTest extends DominionTestCase {

    @Before
    void setUp() {
        super.setUp()
    }

    @Test
    void testNextPlayer() {
        game.nextPlayer()
        game.nextPlayer()

        Turn turn = game.getTurn()
        player = getCurrentPlayer()

        assert game.turnerator.hasNext(), 'There should be a next player'

        assert player.allCards.size() == 10, 'Start game with 10 cards total'
        assert player.hand.size() == 5, 'Start with 5 cards in hand'
        assert player.deck.size() == 5, 'Start with 5 cards in deck'

        kingdom.cardMarket.clear()

        game.nextPlayer()
        assert turn.getPhase() == Phase.END_OF_GAME
    }

    @Test
    void testIsGameOver() {
        Kingdom kingdom = game.kingdom

        kingdom.cardMarket.clear()

        assert game.isGameOver(), 'Game ends if cards are gone.'
    }

}
