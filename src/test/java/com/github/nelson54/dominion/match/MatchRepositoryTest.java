package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.DominionTestData;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.google.common.collect.Iterators;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
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
        this.accountRepository.deleteAll();
        this.matchRepository.deleteAll();

        DominionTestData dta = new DominionTestData();
        List<AccountEntity> accounts = Arrays.asList(dta.getAccountEntityA(), dta.getAccountEntityB());

        accounts = this.accountRepository.saveAll(accounts);

        AccountEntity accountA = accounts.get(0);
        AccountEntity accountB = accounts.get(1);

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
        Iterable<MatchEntity> matchEntities = matchRepository.findByStateIn(Collections.singletonList(MatchState.WAITING_FOR_PLAYERS));
        Iterator<MatchEntity> entityIterator = matchEntities.iterator();

        Assert.assertEquals("Found game waiting for Players", 1, Iterators.size(entityIterator));

        matchEntities = matchRepository.findByStateIn(Arrays.asList(MatchState.WAITING_FOR_PLAYERS, MatchState.IN_PROGRESS));
        entityIterator = matchEntities.iterator();

        Assert.assertEquals("Find games waiting and in progress", 2, Iterators.size(entityIterator));


        matchEntities = matchRepository.findByStateIn(Arrays.asList(MatchState.WAITING_FOR_PLAYERS, MatchState.IN_PROGRESS, MatchState.FINISHED));
        entityIterator = matchEntities.iterator();

        Assert.assertEquals("Find games in all states", 3, Iterators.size(entityIterator));
    }

    @Test
    void findByPlayerId() {
        Iterable<MatchEntity> matchEntities = matchRepository.findByPlayerId(playerId);

        Assert.assertTrue("Found matchEntity", matchEntities.iterator().hasNext());
    }

    @Test
    void create() {
        this.accountRepository.deleteAll();

        DominionTestData dta = new DominionTestData();
        List<AccountEntity> accounts = Arrays.asList(dta.getAccountEntityA(), dta.getAccountEntityB());

        accounts = this.accountRepository.saveAll(accounts);

        AccountEntity account = accounts.get(0);

    }
}