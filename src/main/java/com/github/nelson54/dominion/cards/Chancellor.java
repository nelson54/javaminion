package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.ChancellorEffect;
import com.github.nelson54.dominion.effects.Effect;

public class Chancellor extends ComplexActionCard {

    public Chancellor() {
        super();
        byte moneyCost = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Chancellor");
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();

        choice.setMessage("Would you like to put your deck into your discard pile?");
        choice.setExpectedAnswerType(OptionType.YES_OR_NO);
        choice.setRequired(true);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        Effect effect = new ChancellorEffect();
        effect.setOwner(getOwner());
        effect.setTarget(player);

        return effect;
    }

    @Override
    void play(Player player, Game game) {
        game.trashCard(this);
    }
}
