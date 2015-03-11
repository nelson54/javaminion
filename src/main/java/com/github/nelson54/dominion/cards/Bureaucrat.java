package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.MilitiaEffect;

import java.util.Set;
import java.util.stream.Collectors;


public class Bureaucrat extends ComplexActionAttackCard {

    public Bureaucrat() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Bureaucrat");
    }

    @Override
    Choice getChoiceForTarget(Choice parent, Player target, Game game) {
        Choice choice = new Choice(target, this);
        choice.setMessage("Choose 3 cards to keep in your hand.");

        Set<Card> victoryCardsInHand = target.getHand().stream()
                .filter(card-> card instanceof TreasureCard)
                .collect(Collectors.toSet());

        if(victoryCardsInHand.size() > 0) {

            choice.setCardOptions(victoryCardsInHand);

            choice.setExpectedAnswerType(OptionType.LIST_OF_CARDS);
            choice.setRequired(true);
            choice.setNumber((byte) 3);

            return choice;
        } else {
            choice.setComplete(true);
        }

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new MilitiaEffect();
    }

    @Override
    void play(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        game.giveCardToPlayer("Silver", player);
    }
}
