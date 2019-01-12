package com.github.nelson54.dominion.persistence.entities.match;

import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name="match")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private Long seed;

    @ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
    private List<AccountEntity> players;

    @Column
    private Integer playerCount;

    @Column
    private String turnOrder;

    @Column
    private MatchState state;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private Set<PlayerScoreEntity> scores;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardTypeReferenceEntity> gameCards;

    public MatchEntity() {}

    public Long getId() {
        return this.id;
    }

    public Match toMatch() {
        GameCardSet cardSet = GameCardSet.of(gameCards
                .stream()
                .map(CardTypeReferenceEntity::asCardTypeReference)
                .collect(Collectors.toList()));

        Match match = new Match(id, seed, state, playerCount, cardSet);

        players.stream().map((playerAccount)->{
            if(playerAccount.isAi()) {

                return MatchParticipant.createAi();
            }
            return new MatchParticipant(playerAccount.asAccount());
        }).forEachOrdered(match::addParticipant);

        return match;
    }

    public static MatchEntity ofMatch(Match match) {
        MatchEntity matchEntity = new MatchEntity();

        if(match.getId() != null) {
            matchEntity.id = match.getId();
        }

        matchEntity.playerCount = match.getPlayerCount();

        matchEntity.seed = match.getSeed();

        matchEntity.state = match.getMatchState();

        matchEntity.players = match.getParticipants().stream()
                .map((participant)->AccountEntity.ofAccount(participant.getAccount()))
                .collect(Collectors.toList());



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
}
