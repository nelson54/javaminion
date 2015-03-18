package com.github.nelson54.dominion

import org.junit.Before
import org.junit.Test

/**
 * Created by Derek on 3/14/2015.
 */

class DominionTestCase extends GroovyTestCase {

    Game game
    GameFactory gameFactory
    KingdomFactory kingdomFactory
    Kingdom kingdom
    Player player
    Turn turn

    @Before
    void setUp() {
        gameFactory = new GameFactory()
        kingdomFactory = new KingdomFactory()
        gameFactory.setKingdomFactory(kingdomFactory)

        game = gameFactory.createGame(2)
        turn = game.getTurn()
        turn.setPhase(Phase.ACTION)
        kingdom = game.kingdom
        player = getCurrentPlayer()
    }

    @Test
    void testIgnore(){

    }

    Player getCurrentPlayer(){
        return game.getTurn().getPlayer()
    }
}
