package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.HashSet;
import java.util.Set;

public class LibraryEffect extends Effect {

    Set<Card> setAside;

    public LibraryEffect() {
        super();
        setAside = new HashSet<>();
    }

    @Override
    public boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        if (setAside == null) {
            setAside = new HashSet<>();
        }

        if (response.isYes()) {
            setAside.addAll(getChoice().getParentChoice().getCardOptions());
            target.getDeck().removeAll(getChoice().getParentChoice().getCardOptions());
        } else {
            target.drawXCards(1);
        }

        if (target.getHand().size() >= 7) {
            target.getHand().removeAll(setAside);
            target.getDiscard().addAll(setAside);
            return true;
        }

        return target.getDeck().size() + target.getDiscard().size() == 0;

    }

}
