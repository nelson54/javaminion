package com.github.nelson54.dominion.persistence.entities;


import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

@Entity(name="game")
public class GameEntity {

    @Id
    @Column(name="game_id")
    private String id;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private Set<PlayerEntity> players;

    /*@OneToMany(mappedBy="game")
    private Set<CardEntity> cards;*/
    private Boolean gameOver;

    private GameEntity() {
        players = new HashSet<>();
        //cards = new HashSet<>();
    }

    public static GameEntity ofGame(Game game) {
        GameEntity gameEntity = new GameEntity();

        gameEntity.setId(game.getId());

        game.getPlayers().values().forEach( (player -> {
            PlayerEntity playerEntity = PlayerEntity.ofPlayer(player);
            gameEntity.players.add(playerEntity);
        }) );

        gameEntity.setGameOver(game.getGameOver());

        return gameEntity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlayers(Set<PlayerEntity> players) {
        this.players = players;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    public Game asGame() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Game game = new Game(id);

        for (PlayerEntity playerEntity : players) {
            Player player = playerEntity.asPlayer();
            player.setGame(game);
            game.getPlayers().put(player.getId(), player);
        }

        game.setGameOver(gameOver);

        return game;
    }
}
