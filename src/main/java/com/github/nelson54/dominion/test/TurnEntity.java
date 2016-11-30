package com.github.nelson54.dominion.test;

import javax.persistence.Entity;

@Entity
public class TurnEntity {

    /*@Id
    @GeneratedValue
    private Long id;

    private GameEntity game;
    private Phase phase;
    private PlayerEntity player;
    private Set<CardEntity> revealed;

    public static TurnEntity ofTurn(Turn turn) {
        return null;
    }

    public Turn asTurn(Game game) {
        Turn turn = new Turn();

        turn.setPhase(phase);
        Player player = game.getPlayers().get(this.player.getId());

        player.setCurrentTurn(turn);
        game.setTurn(turn);
        turn.setPlayerId(player.getId());

        Set<Card> revealed = CardEntity.asCards(this.revealed);

        return turn;
    }*/
}
