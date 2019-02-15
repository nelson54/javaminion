package com.github.nelson54.dominion.controllers;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.web.Application;
import com.github.nelson54.dominion.web.controllers.GameController;
import com.github.nelson54.dominion.web.controllers.MatchController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DominionControllerTest extends DominionTestCase {

    @Autowired
    GameController gameController;

    @Autowired
    MatchController matchController;

    @Before
    public void setup() {

        //game = matchController.createMatch(gameModel());
    }

    @Test
    public void testGetGames() {
        //assert gameController.getGames().size() > 0, "games exist"

    }

    @Test
    public void testGetGame() {
        //game = gameController.getGame(game.getId().toString())
    }
}