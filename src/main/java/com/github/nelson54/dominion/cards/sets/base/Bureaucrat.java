package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionAttackCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Bureaucrat extends ComplexActionAttackCard {

    public Bureaucrat(Long id) {
        super(id);
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Bureaucrat");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game)
            throws NoValidChoiceException {
        choice.setMessage("Choose a victory card to put on top of your deck.");

        LinkedHashSet<Long> victoryCardsInHand = target.getHand().stream()
                .filter(card -> card.getCardTypes().contains(CardType.VICTORY))
                .map(Card::getId)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (victoryCardsInHand.size() > 0) {

            choice.setOptions(victoryCardsInHand);

            choice.setExpectedAnswerType(OptionType.CARD);
            choice.setRequired(true);
            choice.setNumber((byte) 1);

            return choice;
        } else {
            target.revealHand();
            throw new NoValidChoiceException();
        }
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new BureaucratEffect();
    }

    @Override
    public void play(Player player, Game game) {
        game.giveCardToPlayer("Silver", player);
    }
}
