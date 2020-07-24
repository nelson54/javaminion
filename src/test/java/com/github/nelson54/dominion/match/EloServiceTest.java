package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.github.nelson54.dominion.user.account.AccountService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations="classpath:application-test.properties")
class EloServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EloService eloService;

}