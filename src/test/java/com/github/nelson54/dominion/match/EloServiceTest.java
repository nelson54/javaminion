package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.github.nelson54.dominion.user.account.AccountService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class EloServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EloService eloService;

    List<Account> accounts;

    @BeforeEach
    void setUp() {
        accounts = new ArrayList<>();

        Account account1 = accountService.findById("1").get();
        Account account2 = accountService.findById("2").get();

        account1.setElo(1000L);
        account2.setElo(1000L);

        accounts.add(account1);
        accounts.add(account2);
    }

    @Test
    void updateEloForAccounts() {
        eloService.updateEloForAccounts(accounts, "1");

        Account account1 = accountService.findById("1").get();
        Account account2 = accountService.findById("2").get();

        Assert.assertTrue("Account 1 has a higher elo",account1.getElo() > 1000L);
        Assert.assertTrue("Account 2 has a higher elo",account2.getElo() < 1000L);
    }
}