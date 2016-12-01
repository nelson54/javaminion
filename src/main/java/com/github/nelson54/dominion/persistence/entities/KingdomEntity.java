package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.cards.types.Card;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.persistence.*;
import java.util.*;

@Entity(name="kingdom")
public class KingdomEntity {

    @Id
    @Column(name="kingdom_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn
    private List<KingdomStackEntity> kingdomStackEntities;

    private KingdomEntity() {
        kingdomStackEntities = new ArrayList<>();
    }

    public static KingdomEntity ofKingdom(Kingdom kingdom) {
        KingdomEntity kingdomEntity = new KingdomEntity();

        kingdom.getCardMarket().keySet().forEach(cardName -> {
            KingdomStackEntity kingdomStackEntity = new KingdomStackEntity(cardName);
            kingdomStackEntity.getCards().addAll(CardEntity.ofCards(kingdom.getCardMarket().get(cardName)));
            kingdomEntity.kingdomStackEntities.add(kingdomStackEntity);
                });

        return kingdomEntity;
    }

    public Kingdom asKingdom() {
        Kingdom kingdom = new Kingdom();
        Map<String, Card> all = new HashMap<>();
        Multimap<String, Card> cardMarket = ArrayListMultimap.create(20, 60);

        kingdom.setAllCards(all);
        kingdom.setCardMarket(cardMarket);

        kingdomStackEntities.forEach(kingdomStackEntity -> {
            Collection<Card> cards = CardEntity.asCards(kingdomStackEntity.getCards());
            cards.forEach(card -> all.put(card.getId(), card));
            cardMarket.putAll(kingdomStackEntity.getName(), cards);
        });

        return kingdom;
    }
}
