package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.cards.sets.base.effects.ChapelEffect;
import com.github.nelson54.dominion.cards.sets.base.effects.Effect;

import java.util.HashSet;
import java.util.Set;

public class Chapel extends ComplexActionCard {

    public Chapel(Long id) {
        super(id);
        byte moneyCost = 2;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Chapel");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        Set<Card> options = new HashSet<>();
        options.addAll(Cards.cardsRemainingInHand(target));

        choice.setGame(game);
        choice.setIsDialog(false);
        choice.setMessage("Choose up to 4 cards to trash.");
        choice.getOptions().addAll(Cards.getIds(options));
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setCardOptions(options);

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        Effect effect = new ChapelEffect();
        effect.setOwner(getOwner());
        effect.setTarget(player);

        return effect;
    }

    @Override
    public void play(Player player, Game game) {
    }

}
