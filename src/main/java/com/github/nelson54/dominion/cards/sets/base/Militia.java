package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.ComplexActionAttackCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.OptionType;
import com.github.nelson54.dominion.cards.types.Effect;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Militia extends ComplexActionAttackCard {

    public Militia(Long id) {
        super(id);

        getCardTypes().add(CardType.ACTION);
        getCardTypes().add(CardType.ATTACK);

        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Militia");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Choose 3 cards to keep in your hand.");

        Set<Card> options = new HashSet<>();

        if (parent == null) {
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

            if (parent.getResponse() != null && parent.getResponse().getCard() != null) {
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
    public Effect getEffect(Player player, Game game) {
        return new MilitiaEffect();
    }

    @Override
    public void play(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        turn.setMoneyPool(turn.getMoneyPool() + 2);
    }
}
