package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.cards.GameCardSet;

import java.util.*;
import java.util.stream.IntStream;

public class Match {
    private Long id;
    private Long seed;
    private byte playerCount;
    private Map<Long, MatchParticipant> participants;
    private GameCardSet cards;

    public Match(byte playerCount, GameCardSet cards) {
        this.seed = new Random().nextLong();
        this.participants = new HashMap<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Match(Long id, Long seed, byte playerCount, GameCardSet cards) {
        this.id = id;
        this.seed = seed;
        this.participants = new HashMap<>();
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
        return new HashSet<>(participants.values());
    }

    public GameCardSet getCards() {
        return cards;
    }

    public void addParticipant (MatchParticipant participant) {
        participants.put(participant.getAccount().getId(), participant);
    }

    public byte getPlayerCount() {
        return playerCount;
    }

    public boolean isReady() {
        return participants.size() >= playerCount;
    }

    public void addAiParticipants(byte n) {
        Collection<AiName> names = AiName.random(n);
        Iterator<AiName> nameIterator = names.iterator();
        IntStream.rangeClosed(1, n).forEach( (i) -> {
            MatchParticipant participant = MatchParticipant.createAi();
            participant.getAccount().setFirstname(nameIterator.next().toString());
            this.addParticipant(participant);
        });
    }

    boolean hasAccount(Account account) {
        return participants.containsKey(account.getId());
    }
}
