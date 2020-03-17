package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class AiPlayerService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account random() {
        String username = AiName.random().name();

        return random(username);
    }

    public Set<Account> random(Integer random) {
        return AiName.random(random)
                .stream()
                .map(AiName::name)
                .map(accountRepository::findByUserUsername)
                .map(Optional::get)
                .map(AccountEntity::asAccount)
                .collect(Collectors.toSet());
    }

    private Account random(String username) {
        return accountRepository
                .findByUserUsername(UUID.randomUUID().toString())
                .map(AccountEntity::asAccount)
                .get();
    }
}
