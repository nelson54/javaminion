package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.ThroneRoomEffect;

import java.util.Set;
import java.util.stream.Collectors;

public class ThroneRoom extends ActionCard {

    public ThroneRoom() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Throne Room");
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        game.trashCard(this);

        Choice choice = new Choice(player, this);
        Effect effect = new ThroneRoomEffect();

        choice.bind(effect);

        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setNumber((byte) 1);
        choice.setRange(Range.EXACTLY);
        choice.setCardOptions(getOptions(player.getHand()));

        effect.setChoice(choice);
        effect.setOwner(getOwner());
        effect.setTarget(player);

        turn.addChoice(choice);
    }


    Set<Card> getOptions(Set<Card> hand){
        return hand.stream()
                .filter(card -> card instanceof ActionCard)
                .map(card -> (ActionCard)card)
                .collect(Collectors.toSet());
    }
}
