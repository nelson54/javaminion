package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.effects.CellarEffect;
import com.github.nelson54.dominion.effects.Effect;

import java.util.HashSet;
import java.util.Set;

public class Cellar extends ComplexActionCard {
    public Cellar(){
        super();
        byte moneyCost = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Cellar");
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        Set<Card> options;

        if(parent == null){
            options = new HashSet<>();
            options.addAll(target.getHand());
        } else {
            options = parent.getCardOptions();

            if(parent.getResponse() != null && parent.getResponse().getCard() != null){
                options.remove(parent.getResponse().getCard());
            }
        }

        choice.setGame(game);
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setRequired(false);
        choice.setRange(Range.ANY);
        choice.setCardOptions(options);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new CellarEffect();
    }

    @Override
    void play(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        turn.setActionPool(turn.getActionPool()+1);
    }
}
