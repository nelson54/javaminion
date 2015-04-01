package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.MineEffect;

import java.util.Set;
import java.util.stream.Collectors;

public class Mine extends ComplexActionCard {

    public Mine() {
        super();
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Mine");
    }

    @Override
    CardState getState(Choice choice){
        if(choice.getParentChoice() == null){
            return CardState.TRASHING_CARD;
        } else {
            return CardState.GAINING_CARD;
        }
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();

        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setIsDialog(false);
        if (choice.getState() == CardState.TRASHING_CARD) {
            choice.setMessage("Choose a card to trash.");

            choice.setCardOptions(
                    getTrashOptions(target.getHand())
            );

            choice.getOptions().addAll(
                    Cards.getIds(choice.getCardOptions())
            );
        } else if (parent != null && choice.getState() == CardState.GAINING_CARD) {
            choice.setMessage("Choose a card to gain.");
            ChoiceResponse response = parent.getResponse();
            choice.setRequired(true);
            choice.setCardOptions(
                    getGainOptions(game.getKingdom(), response.getCard())
            );

            choice.getOptions().addAll(
                    Cards.getIds(choice.getCardOptions())
            );
        }

        return choice;
    }

    private Set<Card> getGainOptions(Kingdom kingdom, Card trashedCard) {
        return kingdom.getTreasureCards().stream()
                .filter(card -> card.getCost().getMoney() <= trashedCard.getCost().getMoney() + 3)
                .collect(Collectors.toSet());
    }

    private Set<Card> getTrashOptions(Set<Card> hand) {
        return Cards.cardsOfType(hand, TreasureCard.class);
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new MineEffect();
    }

    @Override
    void play(Player player, Game game) {

    }
}
