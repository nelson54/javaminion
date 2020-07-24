package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.DominionTestData;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class MatchRepositoryTest {

    private final AccountRepository accountRepository;
    private final MatchRepository matchRepository;

    public String playerId;

    @Autowired
    MatchRepositoryTest(AccountRepository accountRepository, MatchRepository matchRepository) {
        this.accountRepository = accountRepository;
        this.matchRepository = matchRepository;
    }

    @BeforeEach
    void setup() {
        DominionTestData dta = new DominionTestData();
        List<AccountEntity> accounts = Arrays.asList(dta.getAccountEntityA(), dta.getAccountEntityB());

        accountRepository.findByUserUsername(accounts.get(0).getUser().getUsername())
                .ifPresent(accountRepository::delete);

        accountRepository.findByUserUsername(accounts.get(1).getUser().getUsername())
                .ifPresent(accountRepository::delete);

        accounts = this.accountRepository.saveAll(accounts);



        AccountEntity accountA = accounts.get(0);
        AccountEntity accountB = accounts.get(1);

        matchRepository.deleteAll();

        MatchEntity matchEntity = dta.getMatchEntity(accountA, accountB);

        this.matchRepository.save(matchEntity);

        playerId = accountA.getId();

        matchEntity.setId(null);
        matchEntity.setCreatedAt(null);
        matchEntity.setState(MatchState.IN_PROGRESS);
        matchRepository.save(matchEntity);

        matchEntity.setId(null);
        matchEntity.setCreatedAt(null);
        matchEntity.setState(MatchState.FINISHED);
        matchRepository.save(matchEntity);
    }

    @Test
    void findByStateIn() {
        PageRequest pageRequest = PageRequest.of(0,3);

        List<MatchState> gameStates = Collections.singletonList(MatchState.WAITING_FOR_PLAYERS);
        Page<MatchEntity> matchEntities = matchRepository.findByStateIn(gameStates, pageRequest);

        Assert.assertEquals("Found game waiting for Players", 1, matchEntities.getTotalElements());

        gameStates = Arrays.asList(MatchState.WAITING_FOR_PLAYERS, MatchState.IN_PROGRESS);
        matchEntities = matchRepository.findByStateIn(gameStates, pageRequest);

        Assert.assertEquals("Find games waiting and in progress", 2, matchEntities.getTotalElements());

        gameStates = Arrays.asList(
                MatchState.WAITING_FOR_PLAYERS,
                MatchState.IN_PROGRESS,
                MatchState.FINISHED);

        matchEntities = matchRepository.findByStateIn(gameStates, pageRequest);
        Assert.assertEquals("Find games in all states", 3, matchEntities.getTotalElements());
    }

    @Test
    void findByPlayerId() {
        List<MatchEntity> matchEntities = matchRepository.findByPlayerId(playerId);

        Assert.assertEquals("Found matchEntity", 0, matchEntities.size());
    }


}