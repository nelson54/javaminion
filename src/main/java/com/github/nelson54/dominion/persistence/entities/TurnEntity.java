package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TurnEntity {

    @Id
    @Column(name="player_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Phase phase;

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerEntity player;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> revealed;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> play;

    private Integer actionPool;
    private Integer moneyPool;
    private Integer buyPool;

    private TurnEntity() {
        this.revealed = new ArrayList<>();
        this.play = new ArrayList<>();
    }

    public static TurnEntity ofTurn(Turn turn) {
        TurnEntity turnEntity = new TurnEntity();

        turn.getRevealed().values().forEach((card) -> turnEntity.revealed.add(CardEntity.ofCard(card)));
        turn.getPlay().forEach((card) -> turnEntity.play.add(CardEntity.ofCard(card)));

        turnEntity.phase = turn.getPhase();

        turnEntity.actionPool = (int) turn.getActionPool();
        turnEntity.moneyPool = (int) turn.getMoneyPool();
        turnEntity.buyPool = (int) turn.getBuyPool();

        return turnEntity;
    }

    public Turn asTurn(Game game, Player player) {
        Turn turn = new Turn();
        turn.setGame(game);
        turn.setPhase(phase);

        turn.setPlayer(player);
        player.setCurrentTurn(turn);
        game.setTurn(turn);

        turn.setPlayerId(player.getId());
        turn.setPlay(new ArrayList<>());

        turn.setActionPool(actionPool);
        turn.setBuyPool(buyPool);
        turn.setMoneyPool(moneyPool);

        CardEntity.asCards(this.revealed).forEach(card-> turn.getRevealed().put(card.getId(), card));
        turn.getPlay().addAll(CardEntity.asCards(this.play));

        return turn;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public String getUserId() {
        return player.getUserId();
    }
}
