package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.github.nelson54.dominion.user.account.AccountService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class EloServiceTest {

    @Inject
    AccountService accountService;

    @Inject
    AccountRepository accountRepository;

    @Inject
    EloService eloService;

    List<Account> accounts;

    @BeforeEach
    void setUp() {
        accounts = new ArrayList<>();

        Account account1 = accountService.findById(1L).get();
        Account account2 = accountService.findById(2L).get();

        account1.setElo(1000L);
        account2.setElo(1000L);

        accounts.add(account1);
        accounts.add(account2);
    }

    @Test
    void updateEloForAccounts() {
        eloService.updateEloForAccounts(accounts, 1L);

        Account account1 = accountService.findById(1L).get();
        Account account2 = accountService.findById(2L).get();

        Assert.assertTrue("Account 1 has a higher elo",account1.getElo() > 1000L);
        Assert.assertTrue("Account 2 has a higher elo",account2.getElo() < 1000L);
    }
}