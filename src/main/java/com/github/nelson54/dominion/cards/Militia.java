package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.MilitiaEffect;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


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
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Choose 3 cards to keep in your hand.");

        Set<Card> options = new HashSet<>();;

        if(parent == null){
            options.addAll(target.getHand());

            choice.getOptions().addAll(
                    Cards.getIds(options)
            );

        } else {
            options.addAll(
                target
                        .getHand()
                        .stream()
                        .collect(Collectors.toSet())
            );

            if(parent.getResponse() != null && parent.getResponse().getCard() != null){
                options.remove(parent.getResponse().getCard());
            }
        }

        choice.setOptions(Cards.getIds(options));

        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setIsDialog(false);
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
