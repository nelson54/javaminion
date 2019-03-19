package com.github.nelson54.dominion.persistence.entities.match;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "match")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long seed;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<AccountEntity> players;

    @Column
    private Integer playerCount;

    @Column
    private String turnOrder;

    @Column
    private MatchState state;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private Set<PlayerScoreEntity> scores;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<CardTypeReferenceEntity> gameCards;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AccountEntity winner;

    public MatchEntity() {}

    public Long getId() {
        return this.id;
    }

    public Match toMatch() {
        GameCardSet cardSet = GameCardSet.of(gameCards
                .stream()
                .map(CardTypeReferenceEntity::asCardTypeReference)
                .collect(Collectors.toList()));

        Map<Long, AccountEntity> accounts = new HashMap<>();

        Match match = new Match(id, seed, state, playerCount, cardSet);

        players.forEach((playerAccount) -> accounts.put(playerAccount.getId(), playerAccount));

        Arrays.asList(turnOrder.split(",")).stream()
                .map(Long::valueOf)
                .map(accounts::get)
                .map((playerAccount) -> new MatchParticipant(playerAccount.asAccount()))
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

    public AccountEntity findPlayerById(Long id) {
        Map<Long, AccountEntity> playersById = new HashMap<>();
        this.players.forEach((player) -> playersById.put(player.getId(), player));

        return playersById.get(id);
    }

    public void setState(MatchState state) {
        this.state = state;
    }

    public void setScores(Set<PlayerScoreEntity> scores) {
        this.scores = scores;
    }

    public void setWinner(AccountEntity accountEntity) {
        this.winner = accountEntity;
    }
}
