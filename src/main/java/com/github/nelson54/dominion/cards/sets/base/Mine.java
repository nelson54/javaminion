package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.*;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.sets.base.effects.Effect;
import com.github.nelson54.dominion.cards.sets.base.effects.MineEffect;

import java.util.Set;
import java.util.stream.Collectors;

public class Mine extends ComplexActionCard {

    public Mine(Long id) {
        super(id);
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Mine");
    }

    @Override
    public CardState getState(Choice choice) {
        if (choice.getParentChoice() == null) {
            return CardState.TRASHING_CARD;
        } else {
            return CardState.GAINING_CARD;
        }
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();

        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setIsDialog(false);
        if (choice.getState() == CardState.TRASHING_CARD) {
            choice.setMessage("Choose a card to trash.");

            choice.getOptions().addAll(
                    Cards.getIds(getTrashOptions(target.getHand()))
            );
        } else if (parent != null && choice.getState() == CardState.GAINING_CARD) {
            choice.setMessage("Choose a card to gain.");
            ChoiceResponse response = parent.getResponse();
            choice.setRequired(true);

            choice.getOptions().addAll(
                    Cards.getIds(getGainOptions(game.getKingdom(), response.getCard()))
            );
        }

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new MineEffect();
    }

    @Override
    public void play(Player player, Game game) {

    }

    private Set<Card> getGainOptions(Kingdom kingdom, Card trashedCard) {
        return kingdom.getTreasureCards().stream()
                .filter(card -> card.getCost().getMoney() <= trashedCard.getCost().getMoney() + 3)
                .collect(Collectors.toSet());
    }

    private Set<Card> getTrashOptions(Set<Card> hand) {
        return Cards.ofType(hand, TreasureCard.class);
    }
}
