package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.game.commands.CommandService;
import com.github.nelson54.dominion.game.*;
import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.account.RegistrationDto;
import com.github.nelson54.dominion.user.account.AccountService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class MatchServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MatchServiceTest.class);

    @Autowired
    AccountService accountService;

    @Autowired
    MatchService matchService;

    @Autowired
    CommandService commandService;

    @Test
    void part1_createMatch() {
        //commandService.deleteAll();

        RegistrationDto account1 = new RegistrationDto();
        account1.setEmail("kevin@example.com");
        account1.setFirstname("kevin");
        account1.setUsername("kevin");
        account1.setPassword("testing");

        MatchParticipant matchParticipant1 = accountService
                .createAccount(account1)
                .map(MatchParticipant::new)
                .get();

        RegistrationDto account2 = new RegistrationDto();
        account2.setEmail("richard@example.com");
        account2.setFirstname("richard");
        account2.setUsername("richard");
        account2.setPassword("testing");

        MatchParticipant matchParticipant2 = accountService
                .createAccount(account2)
                .map(MatchParticipant::new)
                .get();

        GameCardSet gameCardSet = GameCardSet.byName("First Game");
        Match match = new Match(2, 12345L, gameCardSet);

        match.addParticipant(matchParticipant1);
        match.addParticipant(matchParticipant2);

        match = matchService.createMatch(match);

        String matchId = match.getId();

        Game game1 = matchService.getGame(matchId).get();

        Game game2 = matchService.getGame(matchId).get();

        Set<Long> cardIds1 = game1.getAllCards().keySet();

        Set<Long> cardIds2 = game2.getAllCards().keySet();

        logger.info("Game cards 1: " + cardIds1);
        logger.info("Game cards 2: " + cardIds2);

        Assert.assertEquals(cardIds1, cardIds2);

        Turn turn = game1.getTurn();
        Player player = turn.getPlayer();

        logger.info("Current Player Id: " + turn.getPlayerId());
        logger.info("Current Player Money: " + turn.getMoney());
        logger.info("Current Phase: " + turn.getPhase());


        Assert.assertEquals(Phase.BUY, turn.getPhase());

        Command endPhaseCommand = Command.endPhase(game1, player);

        matchService.applyCommand(game1, endPhaseCommand);

        Player nextTurnPlayer = game1.getTurn().getPlayer();

        Card card = game1.getKingdom().getCardMarket().get("Estate").stream().findFirst().get();

        matchService.applyCommand(game1, Command.buy(game1, nextTurnPlayer, card));

        //Assert.assertNotEquals(turn.getPlayerId(), game1.getTurn().getPlayerId());

        Game nextTurnGame = matchService.getGame(matchId).get();

        nextTurnPlayer = nextTurnGame.getTurn().getPlayer();

        //Assert.assertEquals(player.getId(), nextTurnPlayer.getId());

        Kingdom kingdom = game1.getKingdom();
        kingdom.getCardMarket().clear();

        Game game = matchService.applyCommand(game1, Command.endPhase(game1, nextTurnPlayer));

        Assert.assertTrue(game.isGameOver());
    }
}