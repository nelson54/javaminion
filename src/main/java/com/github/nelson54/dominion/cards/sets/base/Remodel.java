package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.*;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.types.Effect;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Remodel extends ComplexActionCard {

    public Remodel(Long id) {
        super(id);
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Remodel");
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
        choice.setIsDialog(false);
        choice.setExpectedAnswerType(OptionType.CARD);

        if (choice.getState() == CardState.TRASHING_CARD) {
            choice.setMessage("Choose a card to trash.");
            choice.setOptions(Cards.getIds(target.getHand()));
        } else if (choice.getState() == CardState.GAINING_CARD) {
            Card lastChoice = parent.getResponse().getCard();
            choice.setMessage("Choose a card to Gain.");
            choice.setOptions(
                    Cards.getIds(getGainOptions(game, lastChoice.getCost().getMoney()))
            );
        }

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {

        return new RemodelEffect();
    }

    @Override
    public void play(Player player, Game game) {
    }

    Set<Card> getGainOptions(Game game, byte cost) {
        Multimap<String, Card> market = game.getKingdom().getCardMarket();

        Set<Card> options = market.keySet().stream()
                .map(market::get)
                .map(cards -> cards.stream().findAny())
                .map(Optional::get)
                .filter(card -> card.getCost().getMoney() <= cost + 2)
                .collect(Collectors.toSet());

        options.removeAll(game.getTurn().getPlay());

        return options;
    }
}
