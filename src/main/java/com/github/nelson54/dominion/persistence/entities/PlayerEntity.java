package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Player;
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

    /*@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> deck;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<CardEntity> discard;*/

    public PlayerEntity() {
        hand = new ArrayList<>();
        /*deck = new ArrayList<>();
        discard = new ArrayList<>();*/
    }

    public static PlayerEntity ofPlayer(Player player) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.user = UserEntity.ofUser(player.getUser());

        player.getHand().forEach((card -> {
            playerEntity.hand.add(CardEntity.ofCard(card));
        }));

        return playerEntity;
    }

    public Player asPlayer() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Player player = new Player(user.asUser());

        putCardsInPlace(player, hand, player.getHand());

        player.getHand();
        return player;
    }

    private void putCardsInPlace(Player player, Collection<CardEntity> entities, Collection<Card> place) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        for(CardEntity cardEntity: entities) {
            Card card = cardEntity.asCard(player);
            card.setOwner(player);
            place.add(card);
        }
    }
}
