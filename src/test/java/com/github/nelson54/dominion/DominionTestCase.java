package com.github.nelson54.dominion;
import com.github.nelson54.dominion.game.*;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.user.account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class DominionTestCase {

    public Game game;
    public GameFactory gameFactory;
    public KingdomFactory kingdomFactory;
    public Kingdom kingdom;
    public Player player;
    public Turn turn;

    @BeforeEach
    public void setup() {
        kingdomFactory = new KingdomFactory();
        gameFactory = new GameFactory(kingdomFactory);

        Match model = gameModel();
        game = gameFactory.createGame(model);
        turn = game.getTurn();
        turn.setPhase(Phase.ACTION);
        kingdom = game.getKingdom();
        player = getCurrentPlayer();
    }

    public Player getCurrentPlayer(){
        return game.getTurn().getPlayer();
    }

    public Player getNextPlayer(){
        return game.getPlayers().get(2L);
    }

    public Match gameModel(){
        Match match = new Match(2, GameCards.ALL_CARDS.getGameCardSet());

        User user1 = new User("User1", "password", new ArrayList());
        User user2 = new User("User2", "password", new ArrayList());

        match.addParticipant(new MatchParticipant(new Account(1L, user1, "user1@example.com", "User1", false)));
        match.addParticipant(new MatchParticipant(new Account(2L, user2, "user2@example.com", "User2", false)));

        return match;
    }
}
