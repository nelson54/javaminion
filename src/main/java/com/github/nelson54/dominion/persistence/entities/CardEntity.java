package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.exceptions.UnableToBuildCardsException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Entity(name="card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    private String clazz;

    public CardEntity() {
    }

    private CardEntity(String id, String clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public static CardEntity ofCard(Card card) {
        return new CardEntity(card.getId(), card.getClass().toString().split(" ")[1]);
    }

    public Card asCard() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return Cards.createInstance(clazz, id);
    }

    public Card asCard(Player player) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return Cards.createInstance(clazz, id, player);
    }

    public static Collection<Card> asCards(Collection<CardEntity> cards) {
        return cards.stream().map(cardEntity -> {
            try {
                return cardEntity.asCard();
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            throw new UnableToBuildCardsException();
        }).collect(Collectors.toSet());
    }

    public static Set<Card> asCards(Player player, Collection<CardEntity> cards) {
        return cards.stream().map(cardEntity -> {
            try {
                return cardEntity.asCard(player);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            throw new UnableToBuildCardsException();
        }).collect(Collectors.toSet());
    }

    public static Set<CardEntity> ofCards(Collection<Card> cards) {

        return cards.stream()
                .map((CardEntity::ofCard))
                .collect(Collectors.toSet());
    }
}
