package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.user.account.AccountEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Document("match")
public class MatchEntity implements Persistable<String> {

    @Id
    private String id;

    @Field
    private Long seed;

    @DBRef
    private List<AccountEntity> players;

    @Field
    private Integer playerCount;

    @Field
    private String turnOrder;

    @Field
    @Enumerated(EnumType.STRING)
    private MatchState state;

    private Set<PlayerScoreEntity> scores;

    private List<CardTypeReferenceEntity> gameCards;

    @DBRef
    public AccountEntity winner;

    @Field
    @CreatedDate
    public LocalDateTime createdAt;

    @Field
    public LocalDateTime finishedAt;

    private boolean persisted;

    public MatchEntity() {}

    public String getId() {
        return this.id;
    }

    public Match toMatch() {
        GameCardSet cardSet = GameCardSet.of(gameCards
                .stream()
                .map(CardTypeReferenceEntity::asCardTypeReference)
                .collect(Collectors.toList()));

        Map<String, AccountEntity> accounts = new HashMap<>();

        Match match = new Match(id, seed, state, playerCount, cardSet);

        players.forEach((playerAccount) -> accounts.put(playerAccount.getId(), playerAccount));

        Map<String, Long> scores = new HashMap<>();

        if(this.scores != null) {
            this.scores.forEach((score) -> {
                if(score.getAccount() != null)
                scores.put(score.getAccount().getId(), score.getScore());
            });
        }

        match.setScores(scores);
        match.setCreatedAt(createdAt);
        match.setFinishedAt(finishedAt);

        Arrays.stream(turnOrder.split(","))
                .map(accounts::get)
                .map((playerAccount) -> {
                    MatchParticipant mp = new MatchParticipant(playerAccount.asAccount());
                    if(winner != null && winner.getId().equals(playerAccount.getId())) {
                        match.setWinner(mp);
                    }
                    return mp;
                })
                .forEachOrdered(match::addParticipant);



        return match;
    }

    public static MatchEntity ofMatch(Match match) {
        MatchEntity matchEntity = new MatchEntity();

        if (match.getId() != null) {
            matchEntity.id = match.getId();
        }

        matchEntity.playerCount = match.getPlayerCount();

        matchEntity.seed = match.getSeed();

        matchEntity.state = match.getMatchState();

        matchEntity.players = match.getParticipants().stream()
                .map((participant) -> AccountEntity.ofAccount(participant.getAccount()))
                .collect(Collectors.toList());

        if (match.getWinner() != null) {
            matchEntity.winner = AccountEntity.ofAccount(match.getWinner().getAccount());
        }

        matchEntity.turnOrder = match.getTurnOrder()
                .stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(","));

        matchEntity.gameCards = match.getCards().getCards()
                .stream()
                .map(CardTypeReferenceEntity::ofCardTypeReference)
                .collect(Collectors.toList());

        return matchEntity;
    }

    public AccountEntity findPlayerById(String id) {
        Map<String, AccountEntity> playersById = new HashMap<>();
        this.players.forEach((player) -> playersById.put(player.getId(), player));

        return playersById.get(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(MatchState state) {
        this.state = state;
    }

    public MatchState getState() {
        return state;
    }

    public void setScores(Set<PlayerScoreEntity> scores) {
        this.scores = scores;
    }

    public void setWinner(AccountEntity accountEntity) {
        this.winner = accountEntity;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public List<AccountEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<AccountEntity> players) {
        this.players = players;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public String getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(String turnOrder) {
        this.turnOrder = turnOrder;
    }

    public List<CardTypeReferenceEntity> getGameCards() {
        return gameCards;
    }

    public void setGameCards(List<CardTypeReferenceEntity> gameCards) {
        this.gameCards = gameCards;
    }

    public AccountEntity getWinner() {
        return winner;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }
}
