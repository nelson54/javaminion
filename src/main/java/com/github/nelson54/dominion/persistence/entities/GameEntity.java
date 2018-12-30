package com.github.nelson54.dominion.persistence.entities;


import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name="game")
public class GameEntity {

    @Id
    @Column(name="game_id")
    private String id;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private Set<PlayerEntity> players;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private Set<CardEntity> trash;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private TurnEntity turn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private KingdomEntity kingdom;

    private Boolean gameOver;

    private GameEntity() {
        players = new HashSet<>();
        trash = new HashSet<>();
    }

    public static GameEntity ofGame(Game game) {
        GameEntity gameEntity = new GameEntity();
        Map<String, PlayerEntity> playersById = new HashMap<>();
        gameEntity.setId(game.getId());

        game.getPlayers().values().forEach( (player -> {
            PlayerEntity playerEntity = PlayerEntity.ofPlayer(player);
            if(playerEntity.getId().equals(game.getTurn().getPlayerId())) {
                gameEntity.turn = playerEntity.getTurn();
            }
            gameEntity.players.add(playerEntity);
        }) );

        game.getTrash().forEach( (card -> gameEntity.trash.add(CardEntity.ofCard(card) )) );

        gameEntity.setGameOver(game.getGameOver());

        gameEntity.kingdom = KingdomEntity.ofKingdom(game.getKingdom());

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
            Player player = playerEntity.asPlayer(game);
            player.setGame(game);
            game.getPlayers().put(player.getId(), player);

            if(player.getId().equals(turn.getUserId())) {
                game.setTurn(player.getCurrentTurn());
            }
        }

        game.getTrash().addAll(CardEntity.asCards(this.trash));
        game.setGameOver(gameOver);

        game.setKingdom(kingdom.asKingdom());

        Set<Player> turnOrder = new LinkedHashSet<>();

        game.getPlayers().values().stream().collect(Collectors.toList());
        Comparator<Player> byTurnOrder = (player1, player2) -> Integer.compare(
                (int) player1.getOrder(), (int) player2.getOrder());

        game.getPlayers().values().stream().sorted(byTurnOrder).forEach(turnOrder::add);

        game.setTurnOrder(turnOrder);

        PeekingIterator<Player> turnerator = Iterators.peekingIterator(turnOrder.iterator());

        while (turnerator.hasNext() && !turnerator.peek().getId().equals(turn.getUserId())) {
            turnerator.next();
        }

        if(turnerator.hasNext()) {
            turnerator.next();
        }

        game.setTurnerator(turnerator);

        return game;
    }

    public PlayerEntity getPlayerById(String id){
        for (PlayerEntity player : players) {
            if(player.getId().equals(id)) {
                return player;
            }
        }

        throw new RuntimeException("Unable to find user: " + id);
    }
}
