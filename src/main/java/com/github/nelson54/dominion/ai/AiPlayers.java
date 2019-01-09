package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.Player;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AiPlayers {

    public static Account random() {
        String username = AiName.random().name();

        return random(username);
    }

    public static Set<Account> random(Integer random) {
        return AiName.random(random)
                .stream()
                .map(AiName::name)
                .map(AiPlayers::random)
                .collect(Collectors.toSet());
    }

    private static Account random(String username) {
        User user = new User(username, UUID.randomUUID().toString(), new ArrayList<>());

        return new Account(user, username + "@example.com", username, true);
    }
}
