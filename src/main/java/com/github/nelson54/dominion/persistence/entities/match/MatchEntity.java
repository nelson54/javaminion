package com.github.nelson54.dominion.persistence.entities.match;

import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private Long seed;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private Set<AccountEntity> players;

    @Column
    private Integer playerCount;

    @Column
    private Integer aiPlayerCount;

    @Column
    private String turnOrder;

    @Column
    private MatchState state;

    @Column
    private Set<PlayerScoreEntity> scores;

    public MatchEntity() {}

    static MatchEntity ofMatch(Match match) {
        MatchEntity matchEntity = new MatchEntity();

        if(match.getId() != null) {
            matchEntity.id = match.getId();
        }

        matchEntity.seed = match.getSeed();

        matchEntity.state = match.getMatchState();

        matchEntity.players = match.getParticipants().stream()
                .map((participant)->AccountEntity.ofAccount(participant.getAccount()))
                .collect(Collectors.toSet());

        matchEntity.turnOrder = match.getTurnOrder()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        return matchEntity;
    }
}
