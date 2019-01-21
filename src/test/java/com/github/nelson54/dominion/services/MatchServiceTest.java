package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.web.Application;
import com.github.nelson54.dominion.web.dto.RegistrationDto;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MatchServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    MatchService matchService;

    @Test
    void part1_createMatch() {
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
        account1.setEmail("richard@example.com");
        account1.setFirstname("richard");
        account1.setUsername("richard");
        account1.setPassword("testing");

        MatchParticipant matchParticipant2 = accountService
                .createAccount(account2)
                .map(MatchParticipant::new)
                .get();

        GameCardSet gameCardSet = GameCardSet.byName("First Game");
        Match match = new Match(2, 12345L, gameCardSet);

        match.addParticipant(matchParticipant1);
        match.addParticipant(matchParticipant2);

        match = matchService.createMatch(match);

        Game game = matchService.getGame(match.getId()).get();
    }
}