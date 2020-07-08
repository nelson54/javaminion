package com.github.nelson54.dominion;

import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.match.CardTypeReferenceEntity;
import com.github.nelson54.dominion.match.MatchEntity;
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.user.UserEntity;
import com.github.nelson54.dominion.user.account.AccountEntity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DominionTestData {

    private final String USERNAME_A = "USERA";
    private final String USERNAME_B = "USERB";

    private AccountEntity createUserFromName(String name) {
        String email = name + "@example.com";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(name);
        userEntity.setEnabled(true);
        userEntity.setPassword("");

        return new AccountEntity(false, name, email, userEntity);
    }

    public AccountEntity getAccountEntityA() {
        return createUserFromName(USERNAME_A);

    }

    public AccountEntity getAccountEntityB() {
        return createUserFromName(USERNAME_B);
    }

    public MatchEntity getMatchEntity(AccountEntity accountA, AccountEntity accountB) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setState(MatchState.WAITING_FOR_PLAYERS);

        matchEntity.setPlayerCount(2);
        matchEntity.setPlayers(new LinkedList<AccountEntity>(Arrays.asList(accountA, accountB)));

        matchEntity.setTurnOrder(accountA.getId() + "," + accountB.getId());

        List<CardTypeReferenceEntity> cards = RecommendedCards.FIRST_GAME.getReferences()
                .stream()
                .map(CardTypeReferenceEntity::ofCardTypeReference)
                .collect(Collectors.toList());

        matchEntity.setGameCards(cards);

        return matchEntity;
    }
}
