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

    ActionCard councilRoom;
    Turn turn;

    @Mock
    private Game game;

    @Mock
    private Player player;

    @Mock
    private Player player2

    @Before
    void setUp() {
        councilRoom = new CouncilRoom()
        councilRoom.setOwner(player)

        turn = new Turn()
        turn.setPlayer(player)
        turn.setActionPool(1)
        turn.setBuyPool(1)
    }

    @Test
    void testApply() {
        Map players = new HashMap<String, Player>()
        players.put("1", player)
        players.put("2", player2)

        EasyMock.expect(player.getCurrentTurn()).andReturn(turn)
        EasyMock.expect(player.drawXCards(4)).andVoid()
        EasyMock.expect(game.getPlayers()).andReturn(players)
        EasyMock.expect(player2.drawXCards(1)).andVoid()
        EasyMock.expect(player.getId()).andStubReturn("1")
        EasyMock.expect(player2.getId()).andStubReturn("2")

        EasyMock.replay(game)
        EasyMock.replay(player)
        EasyMock.replay(player2)
        councilRoom.apply(player, game)

        assert turn.getBuyPool() == 2, 'player has 2 buys'
    }
}
