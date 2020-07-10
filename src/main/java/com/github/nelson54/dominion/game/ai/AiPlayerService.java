package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.github.nelson54.dominion.user.account.AccountService;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class AiPlayerService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AiPlayerService(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public Set<Account> random(Integer random) {
        Map<String, Account> accounts = new HashMap<>();

        while(accounts.size() != random) {
            AiName aiName = AiName.random();
            String name = aiName.toString();

            accountService.findByUsername(name)
                    .map((account) -> accounts.put(name, account))
                    .orElseThrow();
        }

        return new HashSet<>(accounts.values());
    }

}
