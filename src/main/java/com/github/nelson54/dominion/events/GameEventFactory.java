package com.github.nelson54.dominion.events;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.cards.Card;

public class GameEventFactory {

    public GameEventFactory(Game game) {
        Game game1 = game;
    }

    public GameEvent createEntersHandEvent(Card card) {
        GameEvent gameEvent = new GameEvent();

        gameEvent.setOwner(card.getOwner());
        gameEvent.setTarget(card.getOwner());

        gameEvent.setType(EventType.ENTER_HAND);
        gameEvent.setCard(card);

        return gameEvent;
    }
}
