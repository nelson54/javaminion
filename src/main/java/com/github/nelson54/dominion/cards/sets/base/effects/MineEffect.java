package com.github.nelson54.dominion.cards.sets.base.effects;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.CardState;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Set;

public class MineEffect extends Effect {
    private Card trashedCard;

    @Override
    boolean effect(ChoiceResponse response, Player target, Turn turn, Game game) {
        Card card = response.getCard();
        Choice choice = getChoice();

        if (card != null && choice.getState() == CardState.TRASHING_CARD) {
            trashedCard = response.getCard();
            game.trashCard(trashedCard);
            return false;
        } else if (choice.getState() == CardState.GAINING_CARD) {
            Card gainedCard = response.getCard();
            Card toGive = game.giveCardToPlayer(gainedCard.getName(), target);
            moveCardToHand(target, toGive);
            game.log("Player[" + target.getId() + "] " + target.getName()
                    + " gained the card "
                    + card.getName() + " to their hand from Mine.");

            return true;
        }

        return false;
    }

    private void moveCardToHand(Player player, Card card) {
        Set<Card> discard = player.getDiscard();
        Set<Card> deck = player.getDeck();
        Set<Card> hand = player.getHand();

        discard.remove(card);
        deck.remove(card);
        hand.add(card);
    }
}
