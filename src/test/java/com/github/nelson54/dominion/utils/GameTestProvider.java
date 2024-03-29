package com.github.nelson54.dominion.utils;

import com.github.nelson54.dominion.game.*;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.user.account.Account;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class GameTestProvider {

    public Game createGame() {
        KingdomFactory kingdomFactory = new KingdomFactory();
        GameFactory gameFactory = new GameFactory(kingdomFactory);

        Match match = new Match(2, GameCards.ALL_CARDS.getGameCardSet());

        User user1 = new User("User1", "password", new ArrayList());
        User user2 = new User("User2", "password", new ArrayList());

        match.addParticipant(new MatchParticipant(new Account("1", user1, "user1@example.com", "User1", false)));
        match.addParticipant(new MatchParticipant(new Account("2", user2, "user2@example.com", "User2", false)));

        Game game = gameFactory.createGame(match);
        Turn turn = game.getTurn();
        turn.setPhase(Phase.ACTION);

        return game;
    }
}
