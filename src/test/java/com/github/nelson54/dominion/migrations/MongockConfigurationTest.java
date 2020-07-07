package com.github.nelson54.dominion.migrations;

import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.game.ai.AiName;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MongockConfigurationTest {

    @Autowired
    public AccountRepository accountRepository;

    private List<AccountEntity> loadAccounts() {
        String password = "$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK";
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("AI"));

        return Arrays.stream(AiName.values()).map(Enum::toString).map(username -> {
            String email = username.toLowerCase() + "@example.com";

            User user = new User(username, password, authorities);
            return new Account(user, email, username.toLowerCase(), true);
        })
                .map(AccountEntity::ofAccount)
                .collect(Collectors.toList());
    }

    @Test
    public void initialize() {

        accountRepository.saveAll(loadAccounts());
    }
}