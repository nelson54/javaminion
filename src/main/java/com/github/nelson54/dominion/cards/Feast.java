package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceType;
import com.github.nelson54.dominion.choices.Range;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.FeastEffect;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Feast extends ActionCard {

    public Feast() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Feast");
    }

    @Override
    public void apply(Player player, Game game) {
        Kingdom kingdom = game.getKingdom();
        Turn turn = game.getTurn();
        game.trashCard(this);

        Choice<Card> choice = new Choice<>(player, this);
        Effect<Card> feastEffect = new FeastEffect();

        choice.bind(feastEffect);

        choice.setChoiceType(ChoiceType.CARDS);
        choice.setNumber((byte) 1);
        choice.setRange(Range.EXACTLY);
        choice.setOptions(getOptions(kingdom.getCardMarket()));

        feastEffect.setChoice(choice);
        feastEffect.setOwner(getOwner());
        feastEffect.setTarget(player);

        turn.addChoice(choice);
    }

    Set<Card> getOptions(Multimap<String, Card> market){
        return market.keySet().stream()
                .map(market::get)
                .map(cards -> cards.stream().findAny())
                .map(Optional::get)
                .filter( card -> card.getCost().getMoney() <=5 )
                .collect(Collectors.toSet());
    }
}
