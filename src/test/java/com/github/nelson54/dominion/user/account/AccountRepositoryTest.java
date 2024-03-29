package com.github.nelson54.dominion.user.account;

import com.github.nelson54.dominion.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;


    @Test
    public void findElo() {
        assertEquals(0L, (long) accountRepository.findRank(1001L));
        assertNotEquals(0L, accountRepository.findRank(999L));
    }
}