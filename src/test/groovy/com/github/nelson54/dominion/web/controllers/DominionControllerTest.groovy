package com.github.nelson54.dominion.web.controllers

import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.cards.RecommendedCards
import com.github.nelson54.dominion.web.Application
import org.junit.Test
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import com.github.nelson54.dominion.DominionTestCase
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
class DominionControllerTest extends DominionTestCase {

    @Autowired
    GameController gameController

    @Before
    void setUp() {
        super.setUp()
        game = gameController.createGame(RecommendedCards.FIRST_GAME.getName());
    }

    @Test
    void testGetGames() {
        assert gameController.getGames().size() > 0, "games exist"

    }

    @Test
    void testGetGame() {
        game = gameController.getGame(game.getId().toString())
    }

    @Test
    void testEndPhase() {
        game.getTurn().setPhase(Phase.ACTION)
        Phase pastPhase = game.getTurn().getPhase()
        gameController.endPhase(game.getId().toString())

        assert game.getTurn().getPhase() != pastPhase, "phase ended"
    }
}