package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.nelson54.dominion.cards.sets.baseSet.*;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameCards {

    FIRST_GAME(
            "First Game",
            GameCardSet.of(
                CardTypeReference.of("Cellar", Cellar.class),
                CardTypeReference.of("Market", Market.class),
                CardTypeReference.of("Militia", Militia.class),
                CardTypeReference.of("Mine", Mine.class),
                CardTypeReference.of("Moat", Moat.class),
                CardTypeReference.of("Remodel", Remodel.class),
                CardTypeReference.of("Smithy", Smithy.class),
                CardTypeReference.of("Village", Village.class),
                CardTypeReference.of("Woodcutter", Woodcutter.class),
                CardTypeReference.of("Workshop", Workshop.class)
            )
    ),
    VILLAGE_SQUARE(
            "Village Square",
            GameCardSet.of(
                CardTypeReference.of("Bureaucrat", Bureaucrat.class),
                CardTypeReference.of("Cellar", Cellar.class),
                CardTypeReference.of("Festival", Festival.class),
                CardTypeReference.of("Library", Library.class),
                CardTypeReference.of("Market", Market.class),
                CardTypeReference.of("Remodel", Remodel.class),
                CardTypeReference.of("Smithy", Smithy.class),
                CardTypeReference.of("Throne Room", ThroneRoom.class),
                CardTypeReference.of("Woodcutter", Woodcutter.class),
                CardTypeReference.of("Village", Village.class)
            )
    ),
    BIG_MONEY(
            "Big Money",
            GameCardSet.of(
                CardTypeReference.of("Adventurer", Adventurer.class),
                CardTypeReference.of("Bureaucrat", Bureaucrat.class),
                CardTypeReference.of("Chancellor", Chancellor.class),
                CardTypeReference.of("Chapel", Chapel.class),
                CardTypeReference.of("Feast", Feast.class),
                CardTypeReference.of("Laboratory", Laboratory.class),
                CardTypeReference.of("Market", Market.class),
                CardTypeReference.of("Mine", Mine.class),
                CardTypeReference.of("Moneylender", Moneylender.class),
                CardTypeReference.of("Throne Room", ThroneRoom.class)
            )
    ),
    INTERACTION(
            "Interaction",
            GameCardSet.of(
                CardTypeReference.of("Bureaucrat", Bureaucrat.class),
                CardTypeReference.of("Chancellor", Chancellor.class),
                CardTypeReference.of("Council Room", CouncilRoom.class),
                CardTypeReference.of("Festival", Festival.class),
                CardTypeReference.of("Library", Library.class),
                CardTypeReference.of("Militia", Militia.class),
                CardTypeReference.of("Moat", Moat.class),
                CardTypeReference.of("Spy", Spy.class),
                CardTypeReference.of("Thief", Thief.class),
                CardTypeReference.of("Village", Village.class)
            )
    ),
    SIZE_DISTORTION(
            "Size Distortion",
            GameCardSet.of(
                CardTypeReference.of("Cellar", Cellar.class),
                CardTypeReference.of("Chapel", Chapel.class),
                CardTypeReference.of("Feast", Feast.class),
                CardTypeReference.of("Gardens", Gardens.class),
                CardTypeReference.of("Laboratory", Laboratory.class),
                CardTypeReference.of("Thief", Thief.class),
                CardTypeReference.of("Village", Village.class),
                CardTypeReference.of("Witch", Witch.class),
                CardTypeReference.of("Woodcutter", Woodcutter.class),
                CardTypeReference.of("Workshop", Workshop.class)
            )
    ),
    ALL_CARDS(
            "All Cards",
            GameCardSet.of(
                    CardTypeReference.of("Cellar", Cellar.class),
                    CardTypeReference.of("Chapel", Chapel.class),
                    CardTypeReference.of("Moat", Moat.class),
                    CardTypeReference.of("Chancellor", Chancellor.class),
                    CardTypeReference.of("Woodcutter", Woodcutter.class),
                    CardTypeReference.of("Village", Village.class),
                    CardTypeReference.of("Workshop", Workshop.class),
                    CardTypeReference.of("Bureaucrat", Bureaucrat.class),
                    CardTypeReference.of("Gardens", Gardens.class),
                    CardTypeReference.of("Militia", Militia.class),
                    CardTypeReference.of("Moneylender", Moneylender.class),
                    CardTypeReference.of("Feast", Feast.class),
                    CardTypeReference.of("Spy", Spy.class),
                    CardTypeReference.of("Thief", Thief.class),
                    CardTypeReference.of("Remodel", Remodel.class),
                    CardTypeReference.of("Smithy", Smithy.class),
                    CardTypeReference.of("Throne Room", ThroneRoom.class),
                    CardTypeReference.of("Council Room", CouncilRoom.class),
                    CardTypeReference.of("Festival", Festival.class),
                    CardTypeReference.of("Laboratory", Laboratory.class),
                    CardTypeReference.of("Library", Library.class),
                    CardTypeReference.of("Market", Market.class),
                    CardTypeReference.of("Mine", Mine.class),
                    CardTypeReference.of("Witch", Witch.class),
                    CardTypeReference.of("Adventurer", Adventurer.class)
            )
    );

    String name;
    public GameCardSet gameCardSet;

    GameCards(String name, GameCardSet gameCardSet) {
        this.name = name;
        this.gameCardSet = gameCardSet;
    }

    public String getName() {
        return name;
    }

    public GameCardSet getGameCardSet() {
        return gameCardSet;
    }

    public static GameCards ofName(String name) {
        for (GameCards rc : GameCards.values()) {
            if (rc.getName().equals(name.trim())) {
                return rc;
            }
        }

        return null;
    }
}
