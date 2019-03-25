package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.*;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.cards.sets.base.effects.Effect;
import com.github.nelson54.dominion.cards.sets.base.effects.ThroneRoomEffect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.Set;
import java.util.stream.Collectors;

public class ThroneRoom extends ComplexActionCard {

    public ThroneRoom(Long id) {
        super(id);
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Throne Room");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game)
            throws NoValidChoiceException {

        Set<Card> options = getOptions(target.getHand());
        choice.setCardOptions(options);
        choice.setOptions(Cards.getIds(options));

        choice.setMessage("Choose a card to play twice.");

        if (options.isEmpty()) {
            throw new NoValidChoiceException();
        }

        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setRequired(false);
        choice.setNumber((byte) 1);
        choice.setIsDialog(false);
        choice.setRange(Range.EXACTLY);

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new ThroneRoomEffect();
    }

    @Override
    public void play(Player player, Game game) {

    }

    private Set<Card> getOptions(Set<Card> hand) {
        return hand.stream()
                .filter(card -> card instanceof ActionCard)
                .map(card -> (ActionCard) card)
                .collect(Collectors.toSet());
    }
}
