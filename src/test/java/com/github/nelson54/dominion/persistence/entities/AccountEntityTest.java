package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.web.Application;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountEntityTest {

    private static final Logger logger = Logger.getLogger(AccountEntityTest.class);

    @Inject
    private AccountRepository accountRepository;

    public AccountEntityTest() { }

    @Test
    public void testCreatingAccount() {
        User user = new User("derek", "this is my password", new ArrayList<>());
        Account account = new Account(user, "derek@example.com", "derek", false);

        AccountEntity accountEntity = AccountEntity.ofAccount(account);
        logger.info(accountEntity.getUser());
        accountRepository.save(accountEntity);

        logger.info(accountEntity.toString());
        logger.info(accountEntity.getUser().getPassword());
    }
}