package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.cards.GameCardSet;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.IntStream;

public class Match {
    private Long id;
    private byte playerCount;
    private Map<Long, MatchParticipant> participants;
    private GameCardSet cards;

    public Match(byte playerCount, GameCardSet cards) {

        this.participants = new HashMap<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Match(Long id, byte playerCount, GameCardSet cards) {

        this.participants = new HashMap<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Long getId() {
        return id;
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

    boolean hasUser(User user) {
        return participants.containsKey(user.getUsername());
    }
}
