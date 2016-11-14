package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.cards.GameCardSet;

import java.util.*;
import java.util.stream.IntStream;

public class Match {
    private String id;
    private byte playerCount;
    private Map<String, MatchParticipant> participants;
    private GameCardSet cards;

    public Match(byte playerCount, GameCardSet cards) {
        this.id = UUID.randomUUID().toString();
        this.participants = new HashMap<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public Set<MatchParticipant> getParticipants() {
        return new HashSet<MatchParticipant>(participants.values());
    }

    public GameCardSet getCards() {
        return cards;
    }

    public void addParticipant (MatchParticipant participant) {
        participants.put(participant.getUser().getId(), participant);
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
            MatchParticipant participant = new MatchParticipant();
            participant.getUser().setName(nameIterator.next().toString());
            this.addParticipant(participant);
        });
    }

    boolean hasUser(User user) {
        return participants.containsKey(user.getId());
    }
}
