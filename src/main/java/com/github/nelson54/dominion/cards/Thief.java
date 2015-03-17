package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.SpyEffect;
import com.github.nelson54.dominion.effects.ThiefEffect;

import java.util.HashSet;
import java.util.Set;

public class Thief extends SymmetricActionAttackCard {

    public Thief() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Thief");
    }

    String option1 = "Discard revealed card.";
    String option2 = "Put back on deck.";

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();

        if(parent == null || !parent.isComplete()){
            Set<Card> revealed = target.revealCards(2);
            choice.setCardOptions(revealed);

            choice.setTarget(this.getOwner());
            choice.setCardOptions(revealed);
        } else if (parent.getExpectedAnswerType().equals(OptionType.CARD)) {
            choice.setExpectedAnswerType(OptionType.YES_OR_NO);
            String parentChoiceName = parent.getResponse().getCard().getName();
            choice.setMessage("Would you like to gain the "+parentChoiceName+ " that was trashed?" );
        }
        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new ThiefEffect();
    }

    @Override
    void play(Player player, Game game) {
        Turn turn = game.getTurn();
        player.drawXCards(1);
        turn.setActionPool(turn.getActionPool()+1);
    }
}
