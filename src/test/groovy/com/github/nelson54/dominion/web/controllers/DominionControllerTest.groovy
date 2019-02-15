package com.github.nelson54.dominion.web.controllers

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.web.Application
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DominionControllerTest extends DominionTestCase {

    @Autowired
    GameController gameController

    @Autowired
    MatchController matchController

    @Before
    void setUp() {
        super.setUp()

        //game = matchController.createMatch(gameModel());
    }

    @Test
    void testGetGames() {
        //assert gameController.getGames().size() > 0, "games exist"

    }

    @Test
    void testGetGame() {
        //game = gameController.getGame(game.getId().toString())
    }
}