package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.user.account.Account;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data @Getter @Setter @NoArgsConstructor
public class Match {
    private String id;
    private Long seed;
    private Integer playerCount;
    private LinkedHashSet <MatchParticipant> participants;
    private Map<String, Long> scores;
    private MatchParticipant winner;
    private MatchState matchState;
    private GameCardSet cards;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    public Match(Integer playerCount, GameCardSet cards) {
        this.seed = new Random().nextLong();
        this.participants = new LinkedHashSet<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Match(Integer playerCount, Long seed, GameCardSet cards) {
        this.seed = new Random().nextLong();
        this.participants = new LinkedHashSet<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public Match(String id,
                 Long seed,
                 MatchState matchState,
                 Integer playerCount,
                 GameCardSet cards) {

        this.id = id;
        this.seed = seed;
        this.matchState = matchState;
        this.participants = new LinkedHashSet<>();
        this.playerCount = playerCount;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public Long getSeed() {
        return seed;
    }

    boolean hasAccount(Account account) {
        return getTurnOrder().contains(account.getId());
    }

    public List<MatchParticipant> getParticipants() {
        return new LinkedList<>(this.participants);
    }

    public List<String> getTurnOrder() {
        return this.participants.stream()
                .map((participant) -> participant.getAccount().getId())
                .collect(Collectors.toList());
    }

    public void shuffleTurnOrder() {
        List<MatchParticipant> matchParticipants = new ArrayList<>(this.participants);
        Collections.shuffle(matchParticipants, new Random(this.seed));
        participants = new LinkedHashSet<>(matchParticipants);
    }

    public boolean isReady() {
        return participants.size() >= playerCount;
    }

    public void addAiParticipants(Collection<MatchParticipant> participants) {
        participants.forEach(this::addParticipant);
    }

    public void addParticipant(MatchParticipant participant) {
        participants.add(participant);
    }
}
