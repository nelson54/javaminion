package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.sets.base.effects.Effect;
import com.github.nelson54.dominion.cards.sets.base.effects.LibraryEffect;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


public class Library extends ComplexActionCard {

    public Library(Long id) {
        super(id);
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Library");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        choice.setMessage("Would you like to set this card aside?");

        LinkedHashSet<Long> cards = new LinkedHashSet<>();
        return target.revealCard().map((card) -> {
            cards.add(card.getId());
            choice.setOptions(cards);
            choice.setExpectedAnswerType(OptionType.YES_OR_NO);
            choice.setRequired(true);
            choice.setNumber((byte)3);

            return choice;
        }).get();
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new LibraryEffect();
    }

    @Override
    public void play(Player player, Game game) {

    }
}
