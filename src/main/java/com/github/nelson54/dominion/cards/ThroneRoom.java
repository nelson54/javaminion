package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.ThroneRoomEffect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.Set;
import java.util.stream.Collectors;

public class ThroneRoom extends ComplexActionCard {

    public ThroneRoom() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Throne Room");
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) throws NoValidChoiceException {

        Set<Card> options = getOptions(target.getHand());
        options.remove(this);
        if(options.isEmpty()){
            throw new NoValidChoiceException();
        }

        choice.setCardOptions(options);
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setRequired(false);
        choice.setNumber((byte) 1);
        choice.setRange(Range.EXACTLY);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new ThroneRoomEffect();
    }

    @Override
    void play(Player player, Game game) {

    }

    Set<Card> getOptions(Set<Card> hand) {
        return hand.stream()
                .filter(card -> card instanceof ActionCard)
                .map(card -> (ActionCard) card)
                .collect(Collectors.toSet());
    }
}
