package com.github.nelson54.dominion.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="kingdom_stack")
public class KingdomStackEntity {

    @Id
    @Column(name="kingdom_stack_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    protected String name;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn
    protected Set<CardEntity> cards;

    public KingdomStackEntity() {
    }

    public KingdomStackEntity(String name) {
        this.name = name;
        cards = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CardEntity> getCards() {
        return cards;
    }

    public void setCards(Set<CardEntity> cards) {
        this.cards = cards;
    }
}
