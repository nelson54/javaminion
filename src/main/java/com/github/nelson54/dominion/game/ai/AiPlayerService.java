package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.github.nelson54.dominion.user.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class AiPlayerService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public AiPlayerService(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public Set<Account> random(Integer random) {
        Map<String, Account> accounts = new HashMap<>();

        while(accounts.size() != random) {
            AiName aiName = AiName.random();
            String name = aiName.toString();

            Account account = accountService
                    .findByUsername(name)
                    .orElseThrow();

            accounts.put(account.getId(), account);
        }

        return new HashSet<>(accounts.values());
    }

}
