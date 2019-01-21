package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.cards.GameCardSet;

import java.util.*;
import java.util.stream.Collectors;

public class Match {
    private Long id;
    private Long seed;
    private Integer playerCount;
    private List <MatchParticipant> participants;
    private MatchState matchState;
    private GameCardSet cards;

    public Match(Integer playerCount, GameCardSet cards) {
        this.seed = new Random().nextLong();
        this.participants = new ArrayList<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Match(Integer playerCount, Long seed, GameCardSet cards) {
        this.seed = new Random().nextLong();
        this.participants = new ArrayList<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Match(Long id, Long seed, MatchState matchState, Integer playerCount, GameCardSet cards) {
        this.id = id;
        this.seed = seed;
        this.matchState = matchState;
        this.participants = new ArrayList<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public Long getSeed() {
        return seed;
    }



    public Set<MatchParticipant> getParticipants() {
        return new HashSet<>(this.participants);
    }

    public List<Long> getTurnOrder(){
        return this.participants.stream()
                .map((participant)->participant.getAccount().getId())
                .collect(Collectors.toList());
    }

    public List<Long> shuffleTurnOrder() {
        Collections.shuffle(this.participants, new Random(this.seed));

        return getTurnOrder();
    }

    public GameCardSet getCards() {
        return cards;
    }

    public void addParticipant (MatchParticipant participant) {
        participants.add(participant);
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public boolean isReady() {
        return participants.size()== playerCount;
    }

    public void addAiParticipants(Collection<MatchParticipant> participants) {
        participants.forEach(this::addParticipant);
    }

    public MatchState getMatchState() {
        return matchState;
    }

    public void setMatchState(MatchState matchState) {
        this.matchState = matchState;
    }

    boolean hasAccount(Account account) {
        return getTurnOrder().contains(account.getId());
    }
}
