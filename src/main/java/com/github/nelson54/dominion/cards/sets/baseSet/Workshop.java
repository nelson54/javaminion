package com.github.nelson54.dominion.cards.sets.baseSet;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.Effect;
import com.github.nelson54.dominion.cards.sets.baseSet.effects.FeastEffect;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Workshop extends ComplexActionCard {

    public Workshop() {
        super();
        byte moneyCost = 3;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Workshop");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Choose a card to gain.");
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setNumber((byte) 1);
        choice.setRange(Range.EXACTLY);
        choice.setCardOptions(getOptions(target, game));

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        Effect effect = new FeastEffect();
        effect.setOwner(getOwner());
        effect.setTarget(player);

        return effect;
    }

    @Override
    public void play(Player player, Game game) {
        game.trashCard(this);
    }

    Set<Card> getOptions(Player player, Game game) {
        Multimap<String, Card> market = game.getKingdom().getCardMarket();

        return market.keySet().stream()
                .map(market::get)
                .map(cards -> cards.stream().findAny())
                .map(Optional::get)
                .filter(card -> card.getCost().getMoney() <= 4)
                .collect(Collectors.toSet());
    }
}
