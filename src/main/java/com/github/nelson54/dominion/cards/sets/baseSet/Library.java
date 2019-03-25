package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.Effect;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.LibraryEffect;

import java.util.HashSet;
import java.util.Optional;
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

    public Library(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        choice.setMessage("Would you like to set this card aside?");

        Set<Long> cards = new HashSet<>();
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
