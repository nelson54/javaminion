package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.MilitiaEffect;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class Militia extends ComplexActionAttackCard {

    public Militia() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Militia");
    }

    @Override
    Choice getChoiceForTarget(Choice parent, Player target, Game game) {
        Choice choice = new Choice(target, this);
        choice.setMessage("Choose 3 cards to keep in your hand.");

        choice.setCardOptions(target.getHand());

        choice.setExpectedAnswerType(OptionType.LIST_OF_CARDS);
        choice.setRequired(true);
        choice.setNumber((byte)3);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new MilitiaEffect();
    }

    @Override
    void play(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        turn.setMoneyPool(turn.getMoneyPool()+2);
    }
}
