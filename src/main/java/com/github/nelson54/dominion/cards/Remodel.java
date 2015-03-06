package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceType;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.RemodelEffect;
import com.google.common.collect.Multimap;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Remodel extends ComplexActionCard {

    public Remodel() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Remodel");
    }

    @Override
    Choice getChoiceForTarget(Choice parent, Player target, Game game) {
        Choice choice = new Choice(target, this);

        choice.setChoiceType(ChoiceType.CARD);

        if(parent == null) {
            choice.setCardOptions(target.getHand());
        } else if(parent.getResponse() != null){
            Card lastChoice = parent.getResponse().getCard();
            //Set<Card> oldOptions = parent.getCardOptions();

            choice.setCardOptions(
                    getGainOptions(game, lastChoice.getCost().getMoney())
            );
        }

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {

        return new RemodelEffect();
    }

    @Override
    void play(Player player, Game game) {}

    Set<Card> getTrashCardOptions(Player player){
        return player.getHand();
    }

    Set<Card> getGainOptions(Game game, byte cost){
        Multimap<String, Card> market = game.getKingdom().getCardMarket();

        Set<Card> options = market.keySet().stream()
                .map(market::get)
                .map(cards -> cards.stream().findAny())
                .map(Optional::get)
                .filter( card -> card.getCost().getMoney() <= 5 )
                .collect(Collectors.toSet());

        options.removeAll(game.getTurn().getPlay().values());

        return options;
    }
}
