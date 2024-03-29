package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.CardState;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.SymmetricActionAttackCard;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.OptionType;
import com.github.nelson54.dominion.cards.types.Effect;

import java.util.HashSet;
import java.util.Set;

public class Spy extends SymmetricActionAttackCard {

    private static final String option1 = "Discard revealed card.";
    private static final String option2 = "Put back on deck.";

    public Spy(Long id) {
        super(id);
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Spy");
    }

    @Override
    public CardState getState(Choice choice) {
        if (choice.getParentChoice() == null) {
            return CardState.TRASHING_CARD;
        } else {
            return CardState.GAINING_CARD;
        }
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        return target.revealCard().map((card) -> {
            choice.setDisplayCard(card);
            choice.setMessage("Would you like to discard " + card.getName()
                    + " for player " + card.getOwner().getName());

            choice.setExpectedAnswerType(OptionType.YES_OR_NO);
            choice.setRequired(true);
            Set<String> options = new HashSet<>();
            options.add(option1);
            options.add(option2);

            choice.setTarget(this.getOwner());
            choice.setTextOptions(options);

            return choice;
        }).get();
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new SpyEffect();
    }

    @Override
    public void play(Player player, Game game) {
        Turn turn = game.getTurn();
        player.drawXCards(1);
        turn.setActionPool(turn.getActionPool() + 1);
    }
}
