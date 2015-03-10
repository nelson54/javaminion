package com.github.nelson54.dominion.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;

public class MineEffect extends Effect {
    private Card trashedCard;

    @Override
    boolean effect(ChoiceResponse response, Turn turn, Game game) {
        Player source = response.getSource();
        if (trashedCard == null && response.getCard() != null) {
            trashedCard = response.getCard();
            game.trashCard(trashedCard);
            return false;
        } else if (response.getCard() != null) {
            Card gainedCard = response.getCard();
            Card card = game.giveCardToPlayer(gainedCard.getName(), source);

            moveCardToHand(source, card);

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
