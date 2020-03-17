package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.OptionType;
import com.github.nelson54.dominion.game.choices.Range;
import com.github.nelson54.dominion.cards.types.Effect;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Feast extends ComplexActionCard {

    public Feast(Long id) {
        super(id);
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Feast");
    }

    @Override public Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        choice.setGame(game);
        choice.setMessage("Choose a card to gain.");
        choice.setExpectedAnswerType(OptionType.CARD);
        choice.getOptions().addAll(Cards.getIds(getOptions(target, game)));

        choice.setComplete(false);
        choice.setIsDialog(false);
        choice.setRequired(false);
        choice.setNumber((byte) 1);

        choice.setRange(Range.EXACTLY);

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

    private Set<Card> getOptions(Player player, Game game) {
        Multimap<String, Card> market = game.getKingdom().getCardMarket();

        return market.keySet().stream()
                .map(market::get)
                .map(cards -> cards.stream().findAny())
                .map(Optional::get)
                .filter(card -> card.getCost().getMoney() <= 5)
                .collect(Collectors.toSet());
    }
}
