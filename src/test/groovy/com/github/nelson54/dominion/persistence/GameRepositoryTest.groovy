package com.github.nelson54.dominion.persistence

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.persistence.entities.GameEntity
import com.github.nelson54.dominion.web.Application
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
class GameRepositoryTest extends DominionTestCase {

    @Autowired
    GameRepository gameRepository

    @Before
    void setUp() {
        super.setUp()
    }

    @Test
    void testGameRepository() {
        String id = game.getId();

        GameEntity gameEntitySave = GameEntity.ofGame(game)

        gameRepository.save(gameEntity)

        GameEntity gameEntityRead = gameRepository.findOne(id);

        assertEquals(id, gameEntityRead.asGame().getId())

        assertEquals(game.getPlayers().size(), gameEntityRead.asGame().getPlayers().size())
    }
}
