package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class AiPlayerService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Set<Account> random(Integer random) {
        Map<String, Account> accounts = new HashMap<>();

        while(accounts.size() != random) {
            AiName aiName = AiName.random();
            String name = aiName.toString();
            AccountEntity account = accountRepository.findByUserUsername(name).get();
            accounts.put(name, account.asAccount());
        }

        return new HashSet<>(accounts.values());
    }

}
