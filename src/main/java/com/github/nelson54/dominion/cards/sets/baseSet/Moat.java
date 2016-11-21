package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ActionReactionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Reaction;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.Effect;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.MoatRevealEffect;

public class Moat extends ActionReactionCard {

    public Moat() {
        super();
        byte moneyCost = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Moat");
    }

    @Override
    public void apply(Player player, Game game) {
        player.drawXCards(2);
    }

    @Override
    public void react(Effect effect) {

    }

    @Override
    public void onCardPlayed(Card card) {

    }

    @Override
    public void onCardTrashed(Card card) {

    }

    @Override
    public void onChoice(Choice choice) {
        Effect effect = choice.getEffect();
        if (Cards.isAttackCard(effect.getSource())) {
            effect.getTarget().getChoices().add(getRevealChoice(effect));
        }
    }

    private Choice getRevealChoice (Effect effect) {
        Choice choice = new Choice(effect.getTarget(), effect.getSource(), Reaction.from(effect));
        choice.setMessage(
                "Would you like to reveal Moat to prevent the effect of "
                + effect.getSource().getName()
                + " by player "
                + effect.getOwner().getName()
                + ".");
        choice.setIsDialog(true);
        choice.setExpectedAnswerType(OptionType.YES_OR_NO);

        MoatRevealEffect revealEffect = MoatRevealEffect.create(this, choice);
        choice.setEffect(revealEffect);

        return choice;
    }


}
