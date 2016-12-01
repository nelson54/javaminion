package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.AiPlayer;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.cards.types.Card;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name="player")
public class PlayerEntity {

    @Id
    @Column(name="player_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private UserEntity user;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> hand;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> deck;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> discard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private TurnEntity turn;

    @Column
    private Integer playOrder;

    public PlayerEntity() {
        hand = new ArrayList<>();
        deck = new ArrayList<>();
        discard = new ArrayList<>();
    }

    public static PlayerEntity ofPlayer(Player player) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.user = UserEntity.ofUser(player.getUser());

        putCardEntitiesInPlace(player.getHand(), playerEntity.hand);
        putCardEntitiesInPlace(player.getDeck(), playerEntity.deck);
        putCardEntitiesInPlace(player.getDiscard(), playerEntity.discard);

        playerEntity.playOrder = (int)player.getOrder();

        playerEntity.turn = TurnEntity.ofTurn(player.getCurrentTurn());

        playerEntity.turn.setPlayer(playerEntity);

        return playerEntity;
    }

    public Player asPlayer(Game game) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Player player;
        if(user.isAi()) {
            player = new AiPlayer(user.asUser(), AiStrategies.random());
        } else {
            player = new Player(user.asUser());
        }

        player.setOrder((byte) playOrder.intValue());

        putCardsInPlace(player, hand, player.getHand());
        putCardsInPlace(player, discard, player.getDiscard());
        putCardsInPlace(player, deck, player.getDeck());

        player.setCurrentTurn(turn.asTurn(game, player));

        player.getHand();
        return player;
    }

    public String getUserId() {
        return user.getId();
    }

    private static void putCardsInPlace(Player player, Collection<CardEntity> entities, Collection<Card> place) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        for(CardEntity cardEntity: entities) {
            Card card = cardEntity.asCard(player);
            card.setOwner(player);
            place.add(card);
        }
    }

    private static void putCardEntitiesInPlace (Collection<Card> cards, Collection<CardEntity> cardEntities) {
        cards.forEach((card -> {
            cardEntities.add(CardEntity.ofCard(card));
        }));
    }

    public TurnEntity getTurn() {
        return turn;
    }
}
