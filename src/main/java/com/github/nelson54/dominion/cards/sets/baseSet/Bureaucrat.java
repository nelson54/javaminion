package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionAttackCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.VictoryCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.BureaucratEffect;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.Effect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.Set;
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

    public Bureaucrat(Long id, Player player) {
        this(id);
        super.setId(id);
        super.setOwner(player);
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) throws NoValidChoiceException {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Choose a victory card to put on top of your deck.");

        Set<Card> victoryCardsInHand = target.getHand().stream()
                .filter(card-> card instanceof VictoryCard)
                .collect(Collectors.toSet());

        if(victoryCardsInHand.size() > 0) {

            choice.setOptions(Cards.getIds(victoryCardsInHand));

            choice.setExpectedAnswerType(OptionType.CARD);
            choice.setRequired(true);
            choice.setNumber((byte) 3);

            return choice;
        } else {
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
