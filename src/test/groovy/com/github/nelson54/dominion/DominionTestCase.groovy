package com.github.nelson54.dominion

import com.github.nelson54.dominion.cards.RecommendedCards
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

        com.github.nelson54.dominion.web.gamebuilder.Game gb = gameModel()

        game = gameFactory.createGameAllCards(gb)
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

    com.github.nelson54.dominion.web.gamebuilder.Game gameModel(){
        com.github.nelson54.dominion.web.gamebuilder.Game gb = new com.github.nelson54.dominion.web.gamebuilder.Game()

        gb.setCardSet("Big Money")
        Set<com.github.nelson54.dominion.web.gamebuilder.Player> players = new HashSet<>()

        com.github.nelson54.dominion.web.gamebuilder.Player p1 = new com.github.nelson54.dominion.web.gamebuilder.Player();
        p1.setId(UUID.randomUUID().toString())
        p1.setAi(false);

        com.github.nelson54.dominion.web.gamebuilder.Player p2 = new com.github.nelson54.dominion.web.gamebuilder.Player();
        p2.setId(UUID.randomUUID().toString())
        p2.setAi(false);

        players.add(p1)
        players.add(p2)

        gb.setPlayers(players)

        return gb
    }
}
