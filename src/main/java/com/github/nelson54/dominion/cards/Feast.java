package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.FeastEffect;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Feast extends ComplexActionCard {

    public Feast() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Feast");
    }

    @Override
    Choice getChoiceForTarget(Choice parent, Player target, Game game) {
        Choice choice = new Choice(target, this);
        choice.setGame(game);
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.setRequired(false);
        choice.setNumber((byte) 1);
        choice.setRange(Range.EXACTLY);
        choice.setCardOptions(getOptions(target, game));
        choice.setComplete(false);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        Effect effect = new FeastEffect();
        effect.setOwner(getOwner());
        effect.setTarget(player);

        return effect;
    }

    @Override
    void play(Player player, Game game) {
        game.trashCard(this);
    }

    Set<Card> getOptions(Player player, Game game) {
        Multimap<String, Card> market = game.getKingdom().getCardMarket();

        return market.keySet().stream()
                .map(market::get)
                .map(cards -> cards.stream().findAny())
                .map(Optional::get)
                .filter(card -> card.getCost().getMoney() <= 5)
                .collect(Collectors.toSet());
    }
}
