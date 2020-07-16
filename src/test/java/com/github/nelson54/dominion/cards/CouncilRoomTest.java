package com.github.nelson54.dominion.cards;
import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.cards.sets.base.CouncilRoom;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CouncilRoomTest extends DominionTestCase {

    private ActionCard councilRoom;
    private Turn turn;
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setup() {

        this.game = EasyMock.createMock(Game.class);
        this.player1 = EasyMock.createMock(Player.class);
        this.player2 = EasyMock.createMock(Player.class);

        councilRoom = new CouncilRoom(1L);
        councilRoom.setOwner(player1);

        turn = new Turn();
        turn.setPlayer(player1);
        turn.setActionPool(1);
        turn.setBuyPool(1);
    }

    @Test
    public void testApply() {
        Map<String, Player> players = new HashMap<>();
        players.put("1", player1);
        players.put("2", player2);

        EasyMock.expect(player1.getCurrentTurn()).andReturn(turn);

        player1.drawXCards(4);
        EasyMock.expectLastCall();

        EasyMock.expect(game.getPlayers()).andReturn(players);

        player2.drawXCards(1);
        EasyMock.expectLastCall();

        EasyMock.expect(player1.getId()).andStubReturn("1");
        EasyMock.expect(player2.getId()).andStubReturn("2");

        EasyMock.replay(game);
        EasyMock.replay(player1);
        EasyMock.replay(player2);
        councilRoom.apply(player1, game);

        assertEquals(2, turn.getBuyPool(), "player has 2 buys");
    }
}
