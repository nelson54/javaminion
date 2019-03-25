package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.cards.sets.base.effects.CellarEffect;
import com.github.nelson54.dominion.cards.sets.base.effects.Effect;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Cellar extends ComplexActionCard {
    public Cellar(Long id) {
        super(id);
        byte moneyCost = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Cellar");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        Set<Card> options;
        choice.setIsDialog(false);
        choice.setMessage("Choose any number of cards to discard.");
        if (parent == null) {
            options = new HashSet<>();
            options.addAll(target.getHand());

            choice.getOptions().addAll(
                    Cards.getIds(options)
            );
        } else {
            options = parent.getCardOptions();

            if (parent.getResponse() != null && parent.getResponse().getCard() != null) {
                options.remove(parent.getResponse().getCard());
            }
        }

        choice.setGame(game);
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setIsDialog(false);
        choice.setRequired(false);
        choice.setRange(Range.ANY);
        choice.setOptions(options.stream().map(Card::getId).collect(Collectors.toSet()));

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new CellarEffect();
    }

    @Override
    public void play(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        turn.setActionPool(turn.getActionPool() + 1);
    }
}
