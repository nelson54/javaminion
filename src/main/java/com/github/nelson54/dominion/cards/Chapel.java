package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.ChapelEffect;
import com.github.nelson54.dominion.effects.Effect;

import java.util.HashSet;
import java.util.Set;

public class Chapel extends ComplexActionCard {

    public Chapel() {
        super();
        byte moneyCost = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Chapel");
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        Set<Card> options = new HashSet<>();
        choice.setGame(game);
        options.addAll(Cards.cardsRemainingInHand(target));

        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setCardOptions(options);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        Effect effect = new ChapelEffect();
        effect.setOwner(getOwner());
        effect.setTarget(player);

        return effect;
    }

    @Override
    void play(Player player, Game game) {
    }

}
