package com.github.nelson54.dominion

import com.github.nelson54.dominion.cards.GameCards
import com.github.nelson54.dominion.match.Match
import com.github.nelson54.dominion.match.MatchParticipant
import org.junit.Test
import org.springframework.security.core.userdetails.User

class DominionTestCase extends GroovyTestCase {

    Game game
    GameFactory gameFactory
    KingdomFactory kingdomFactory
    Kingdom kingdom
    Player player
    Turn turn

    void setUp() {
        kingdomFactory = new KingdomFactory()
        gameFactory = new GameFactory(kingdomFactory, null)

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

    Player getNextPlayer(){
        return game.getPlayers().get(2L)
    }

    Match gameModel(){
        Match match = new Match((byte)2, GameCards.ALL_CARDS.gameCardSet)

        User user1 = new User("user 1", "password", new ArrayList())
        User user2 = new User("user 2", "password", new ArrayList())

        match.addParticipant(new MatchParticipant(new Account(1, user1, "email", "first", false)))
        match.addParticipant(new MatchParticipant(new Account(2, user2, "email", "first", false)))

        return match
    }
}
