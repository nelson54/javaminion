package com.github.nelson54.dominion.web.controllers

import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.cards.RecommendedCards
import com.github.nelson54.dominion.web.Application
import com.github.nelson54.dominion.web.gamebuilder.Game
import com.github.nelson54.dominion.web.gamebuilder.Player
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
        Game build = new Game()
        Player p1 = new Player()
        p1.setAi(true)

        Player p2 = new Player()
        p2.setAi(true)

        List<Player> players = new ArrayList<>()

        players.add(p1)
        players.add(p2)

        build.setPlayers(players)

        build.setCardSet(RecommendedCards.FIRST_GAME.getName())
        build.setCount(2)

        game = gameController.createGame(build);
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