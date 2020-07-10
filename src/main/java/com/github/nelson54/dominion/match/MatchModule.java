package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Component @Configuration
public class MatchModule {

    @Autowired
    void configureMapper(ModelMapper modelMapper) {
        modelMapper.addConverter((conversion) -> {
            Match match = conversion.getSource();
            MatchEntity matchEntity = new MatchEntity();

            if (match.getId() != null) {
                matchEntity.setId(match.getId());
            }

            matchEntity.setPlayerCount(match.getPlayerCount());

            matchEntity.setSeed(match.getSeed());

            matchEntity.setState(match.getMatchState());

            matchEntity.setPlayers(match.getParticipants().stream()
                    .map((participant) -> modelMapper.map(participant.getAccount(), AccountEntity.class))
                    .collect(Collectors.toList()));

            if (match.getWinner() != null) {
                matchEntity.winner = modelMapper.map(match.getWinner().getAccount(), AccountEntity.class);
            }

            matchEntity.setTurnOrder(match.getTurnOrder()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.joining(",")));

            matchEntity.setGameCards(match.getCards().getCards()
                    .stream()
                    .map(CardTypeReferenceEntity::ofCardTypeReference)
                    .collect(Collectors.toList()));

            return matchEntity;
        }, Match.class, MatchEntity.class);

        modelMapper.addConverter((conversion) -> {
            MatchEntity matchEntity = conversion.getSource();

            GameCardSet cardSet = GameCardSet.of(matchEntity.getGameCards()
                    .stream()
                    .map(CardTypeReferenceEntity::asCardTypeReference)
                    .collect(Collectors.toList()));

            Map<String, AccountEntity> accounts = new HashMap<>();

            Match match = new Match(
                    matchEntity.getId(),
                    matchEntity.getSeed(),
                    matchEntity.getState(),
                    matchEntity.getPlayerCount(),
                    cardSet
            );

            matchEntity.getPlayers()
                    .forEach((playerAccount) -> accounts.put(playerAccount.getId(), playerAccount));

            Map<String, Long> scores = new HashMap<>();

            if(matchEntity.getScores() != null) {
                matchEntity.getScores().forEach((score) -> {
                    if(score.getAccount() != null)
                        scores.put(score.getAccount().getId(), score.getScore());
                });
            }

            match.setScores(scores);
            match.setCreatedAt(matchEntity.getCreatedAt());
            match.setFinishedAt(matchEntity.getFinishedAt());

            Arrays.stream(matchEntity.getTurnOrder().split(","))
                    .map(accounts::get)
                    .map((playerAccount) -> {
                        Account account = modelMapper.map(playerAccount, Account.class);
                        MatchParticipant mp = new MatchParticipant(account);
                        if(matchEntity.getWinner() != null && matchEntity.getWinner().getId().equals(playerAccount.getId())) {
                            match.setWinner(mp);
                        }
                        return mp;
                    })
                    .forEachOrdered(match::addParticipant);



            return match;
        }, MatchEntity.class, Match.class);
    }
}
