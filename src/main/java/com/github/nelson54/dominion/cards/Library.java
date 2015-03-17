package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.LibraryEffect;

import java.util.HashSet;
import java.util.Set;


public class Library extends ComplexActionCard {

    public Library() {
        super();
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Library");
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Would you like to set this card aside?");

        Set<Card> card = new HashSet<>();
        card.add(target.revealCard());

        choice.setCardOptions(card);

        choice.setExpectedAnswerType(OptionType.YES_OR_NO);
        choice.setRequired(true);
        choice.setNumber((byte)3);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new LibraryEffect();
    }

    @Override
    void play(Player player, Game game) {

    }
}
