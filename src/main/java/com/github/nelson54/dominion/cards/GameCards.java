package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.nelson54.dominion.cards.sets.baseSet.*;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameCards {

    FIRST_GAME(
            "First Game",
            GameCardSet.of(
                GenericCardReference.of("Cellar", Cellar.class),
                GenericCardReference.of("Market", Market.class),
                GenericCardReference.of("Militia", Militia.class),
                GenericCardReference.of("Mine", Mine.class),
                GenericCardReference.of("Moat", Moat.class),
                GenericCardReference.of("Remodel", Remodel.class),
                GenericCardReference.of("Smithy", Smithy.class),
                GenericCardReference.of("Village", Village.class),
                GenericCardReference.of("Woodcutter", Woodcutter.class),
                GenericCardReference.of("Workshop", Workshop.class)
            )
    ),
    VILLAGE_SQUARE (
            "Village Square",
            GameCardSet.of(
                GenericCardReference.of("Bureaucrat", Bureaucrat.class),
                GenericCardReference.of("Cellar", Cellar.class),
                GenericCardReference.of("Festival", Festival.class),
                GenericCardReference.of("Library", Library.class),
                GenericCardReference.of("Market", Market.class),
                GenericCardReference.of("Remodel", Remodel.class),
                GenericCardReference.of("Smithy", Smithy.class),
                GenericCardReference.of("Throne Room", ThroneRoom.class),
                GenericCardReference.of("Woodcutter", Woodcutter.class),
                GenericCardReference.of("Village", Village.class)
            )
    ),
    BIG_MONEY (
            "Big Money",
            GameCardSet.of(
                GenericCardReference.of("Adventurer", Adventurer.class),
                GenericCardReference.of("Bureaucrat", Bureaucrat.class),
                GenericCardReference.of("Chancellor", Chancellor.class),
                GenericCardReference.of("Chapel", Chapel.class),
                GenericCardReference.of("Feast", Feast.class),
                GenericCardReference.of("Laboratory", Laboratory.class),
                GenericCardReference.of("Market", Market.class),
                GenericCardReference.of("Mine", Mine.class),
                GenericCardReference.of("Moneylender", Moneylender.class),
                GenericCardReference.of("Throne Room", ThroneRoom.class)
            )
    ),
    INTERACTION (
            "Interaction",
            GameCardSet.of(
                GenericCardReference.of("Bureaucrat", Bureaucrat.class),
                GenericCardReference.of("Chancellor", Chancellor.class),
                GenericCardReference.of("Council Room", CouncilRoom.class),
                GenericCardReference.of("Festival", Festival.class),
                GenericCardReference.of("Library", Library.class),
                GenericCardReference.of("Militia", Militia.class),
                GenericCardReference.of("Moat", Moat.class),
                GenericCardReference.of("Spy", Spy.class),
                GenericCardReference.of("Thief", Thief.class),
                GenericCardReference.of("Village", Village.class)
            )
    ),
    SIZE_DISTORTION (
            "Size Distortion",
            GameCardSet.of(
                GenericCardReference.of("Cellar", Cellar.class),
                GenericCardReference.of("Chapel", Chapel.class),
                GenericCardReference.of("Feast", Feast.class),
                GenericCardReference.of("Gardens", Gardens.class),
                GenericCardReference.of("Laboratory", Laboratory.class),
                GenericCardReference.of("Thief", Thief.class),
                GenericCardReference.of("Village", Village.class),
                GenericCardReference.of("Witch", Witch.class),
                GenericCardReference.of("Woodcutter", Woodcutter.class),
                GenericCardReference.of("Workshop", Workshop.class)
            )
    );

    String name;
    GameCardSet gameCardSet;

    GameCards(String name, GameCardSet gameCardSet){
        this.name = name;
        this.gameCardSet = gameCardSet;
    }

    public String getName() {
        return name;
    }

    public GameCardSet getGameCardSet() {
        return gameCardSet;
    }

    public static GameCards ofName(String name){
        for(GameCards rc : GameCards.values()) {
            if(rc.getName().equals(name.trim())){
                return rc;
            }
        }

        return null;
    }
}
