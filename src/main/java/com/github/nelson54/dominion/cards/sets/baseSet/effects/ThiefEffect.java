package com.github.nelson54.dominion.cards.sets.baseSet.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;

public class ThiefEffect extends Effect {
    private Card trashedCard;

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Player source = response.getSource();
        if (trashedCard == null && response.getCard() != null) {
            trashedCard = response.getCard();
            game.trashCard(trashedCard);
            return false;
        } else if (trashedCard != null && response.isYes()) {
            trashedCard.setOwner(target);
            game.getTrash().remove(trashedCard);
            target.getDiscard().add(trashedCard);

            return true;
        } else if (!response.isYes()) {
            return true;
        }

        return false;
    }

    private void moveCardToHand(Player player, Card card) {
        Set<Card> discard = player.getDiscard();
        Set<Card> hand = player.getHand();

        discard.remove(card);
        hand.add(card);
    }
}
