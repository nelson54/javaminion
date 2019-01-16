package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.ChancellorEffect;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.Effect;

public class Chancellor extends ComplexActionCard {

    public Chancellor(Long id) {
        super(id);
        byte moneyCost = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Chancellor");
    }

    public Chancellor(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }


    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();

        choice.setMessage("Would you like to put your deck into your discard pile?");
        choice.setExpectedAnswerType(OptionType.YES_OR_NO);
        choice.setRequired(true);

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        Effect effect = new ChancellorEffect();
        effect.setOwner(getOwner());
        effect.setTarget(player);

        return effect;
    }

    @Override
    public void play(Player player, Game game) {
        long money = player.getCurrentTurn().getMoneyPool();
        player.getCurrentTurn().setMoneyPool(money + 2);
    }
}
