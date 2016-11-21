package com.github.nelson54.dominion

import com.github.nelson54.dominion.cards.GameCardSet
import com.github.nelson54.dominion.cards.GameCards
import com.github.nelson54.dominion.match.Match
import com.github.nelson54.dominion.match.MatchParticipant
import org.junit.Test

class DominionTestCase extends GroovyTestCase {

    Game game
    GameFactory gameFactory
    KingdomFactory kingdomFactory
    Kingdom kingdom
    Player player
    Turn turn

    void setUp() {
        gameFactory = new GameFactory()
        kingdomFactory = new KingdomFactory()
        gameFactory.setKingdomFactory(kingdomFactory)

        Match model = gameModel()
        game = gameFactory.createGame(model)
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

    Match gameModel(){
        Match match = new Match((byte)2, GameCards.ALL_CARDS.gameCardSet)
        match.addParticipant(new MatchParticipant(new User(UUID.randomUUID().toString(), "p1")))
        match.addParticipant(new MatchParticipant(new User(UUID.randomUUID().toString(), "p2")))

        return match
    }
}
