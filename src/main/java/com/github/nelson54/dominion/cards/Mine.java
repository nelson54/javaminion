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
    Choice getChoiceForTarget(Choice parent, Player target, Game game) {
        Choice choice = new Choice(target, this);

        choice.setExpectedAnswerType(OptionType.CARD);

        if (parent == null) {
            getTrashOptions(target.getHand());
        } else if (parent.getResponse() != null) {
            ChoiceResponse response = parent.getResponse();

            choice.setCardOptions(
                    getGainOptions(game.getKingdom(), response.getCard())
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
