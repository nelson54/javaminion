package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.nelson54.dominion.cards.sets.base.*;
import com.github.nelson54.dominion.cards.types.Card;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RecommendedCards {

    RANDOM(
            "Random"
    ),
    FIRST_GAME(
            "First Game",
            Cellar.class,
            Market.class,
            Militia.class,
            Mine.class,
            Moat.class,
            Remodel.class,
            Smithy.class,
            Village.class,
            Woodcutter.class,
            Workshop.class
    ),
    VILLAGE_SQUARE(
            "Village Square",
            Bureaucrat.class,
            Cellar.class,
            Festival.class,
            Library.class,
            Market.class,
            Remodel.class,
            Smithy.class,
            ThroneRoom.class,
            Woodcutter.class,
            Village.class
    ),
    BIG_MONEY(
            "Big Money",
            Adventurer.class,
            Bureaucrat.class,
            Chancellor.class,
            Chapel.class,
            Feast.class,
            Laboratory.class,
            Market.class,
            Mine.class,
            Moneylender.class,
            ThroneRoom.class
    ),
    INTERACTION(
            "Interaction",
            Bureaucrat.class,
            Chancellor.class,
            CouncilRoom.class,
            Festival.class,
            Library.class,
            Militia.class,
            Moat.class,
            Spy.class,
            Thief.class,
            Village.class
    ),
    SIZE_DISTORTION(
            "Size Distortion",
            Cellar.class,
            Chapel.class,
            Feast.class,
            Gardens.class,
            Laboratory.class,
            Thief.class,
            Village.class,
            Witch.class,
            Woodcutter.class,
            Workshop.class
    ),
    POWER(
            "Power",
            Moat.class,
            Chapel.class,
            Feast.class,
            Village.class,
            Laboratory.class,
            Militia.class,
            Market.class,
            Witch.class,
            Mine.class,
            Bureaucrat.class
    );

    String name;
    Class<? extends Card>[] cards;

    @SafeVarargs
    RecommendedCards(String name, Class<? extends Card> ... cards) {
        this.name = name;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Card>[] getCards() {
        return cards;
    }

    public void setCards(Class<? extends Card>[] cards) {
        this.cards = cards;
    }

    public static RecommendedCards ofName(String name) {
        for (RecommendedCards rc : RecommendedCards.values()) {
            if (rc.getName().equals(name.trim())) {
                return rc;
            }
        }

        return null;
    }
}
