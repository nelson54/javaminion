package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.effects.SpyEffect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spy extends SymmetricActionAttackCard {

    public Spy() {
        super();
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Spy");
    }

    String option1 = "Discard revealed card.";
    String option2 = "Put back on deck.";

    @Override
    Choice getChoiceForTarget(Choice choice, Player target, Game game) {
        Card revealed = target.revealCard();
        choice.setDisplayCard(revealed);

        Set<String> options = new HashSet<>();
        options.add(option1);
        options.add(option2);


        choice.setTarget(this.getOwner());
        choice.setTextOptions(options);

        return choice;
    }

    @Override
    Effect getEffect(Player player, Game game) {
        return new SpyEffect();
    }

    @Override
    void play(Player player, Game game) {
        Turn turn = game.getTurn();
        player.drawXCards(1);
        turn.setActionPool(turn.getActionPool()+1);
    }
}
