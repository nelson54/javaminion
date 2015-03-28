package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.ThiefEffect;

import java.util.Set;

public class Thief extends SymmetricActionAttackCard {

    String message1 = "Choose a card to trash.";

    public Thief() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Thief");
    }

    @Override
    CardState getState(Choice choice){
        if(choice.getParentChoice() == null){
            return CardState.TRASHING_CARD;
        } else if (choice.getParentChoice().getState().equals(CardState.TRASHING_CARD)){
            return CardState.GAINING_CARD;
        } else {
            return CardState.RESOLVING;
        }
    }

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();

        if(choice.getState().equals(CardState.TRASHING_CARD)){
            Set<Card> revealed = target.revealCards(2);

            if(revealed.stream().filter(c->c instanceof TreasureCard).count() > 0) {
                choice.setCardOptions(revealed);
                choice.setMessage(message1);
                choice.setTarget(this.getOwner());
                choice.setCardOptions(revealed);
                choice.setExpectedAnswerType(OptionType.CARD);
            } else {
                choice.setComplete(true);
            }

        } else if (choice.getState().equals(CardState.GAINING_CARD)) {
            choice.setExpectedAnswerType(OptionType.YES_OR_NO);
            String parentChoiceName = parent.getCardOptions().stream().findFirst().get().getName();
            choice.setRequired(true);
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
