package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.BureaucratEffect;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

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
    Choice getChoiceForTarget(Choice choice, Player target, Game game) throws NoValidChoiceException {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Choose a victory card to put on top of your deck.");

        Set<Card> victoryCardsInHand = target.getHand().stream()
                .filter(card-> card instanceof VictoryCard)
                .collect(Collectors.toSet());

        if(victoryCardsInHand.size() > 0) {

            choice.setCardOptions(victoryCardsInHand);

            choice.setExpectedAnswerType(OptionType.CARD);
            choice.setRequired(true);
            choice.setNumber((byte) 3);

            return choice;
        } else {
            throw new NoValidChoiceException();
        }
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new BureaucratEffect();
    }

    @Override
    void play(Player player, Game game) {
        game.giveCardToPlayer("Silver", player);
    }
}
