package com.github.nelson54.dominion.cards

import com.github.nelson54.dominion.Game
import com.github.nelson54.dominion.Player
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.sets.baseSet.CouncilRoom
import com.github.nelson54.dominion.cards.types.ActionCard
import org.easymock.EasyMock
import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(EasyMockRunner.class)
class CouncilRoomTest extends EasyMockSupport {

    ActionCard councilRoom
    Turn turn

    @Mock
    private Game game

    @Mock
    private Player player1

    @Mock
    private Player player2

    @Before
    void setUp() {
        councilRoom = new CouncilRoom(1L)
        councilRoom.setOwner(player1)

        turn = new Turn()
        turn.setPlayer(player1)
        turn.setActionPool(1)
        turn.setBuyPool(1)
    }

    @Test
    void testApply() {
        Map players = new HashMap<Long, Player>()
        players.put(1, player1)
        players.put(2, player2)

        EasyMock.expect(player1.getCurrentTurn()).andReturn(turn)
        EasyMock.expect(player1.drawXCards(4)).andVoid()
        EasyMock.expect(game.getPlayers()).andReturn(players)
        EasyMock.expect(player2.drawXCards(1)).andVoid()
        EasyMock.expect(player1.getId()).andStubReturn(1L)
        EasyMock.expect(player2.getId()).andStubReturn(2L)

        EasyMock.replay(game)
        EasyMock.replay(player1)
        EasyMock.replay(player2)
        councilRoom.apply(player1, game)

        assert turn.getBuyPool() == 2, 'player has 2 buys'
    }
}
