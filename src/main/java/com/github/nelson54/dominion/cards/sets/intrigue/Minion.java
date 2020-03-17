package com.github.nelson54.dominion.cards.sets.intrigue;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.Effect;
import com.github.nelson54.dominion.cards.types.ComplexActionAttackCard;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.HashSet;
import java.util.Set;


public class Minion extends ComplexActionAttackCard {

    public Minion(Long id) {
        super(id);
        byte moneyCost = 5;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Minion");
    }

    @Override
    public Choice getChoiceForTarget(Choice choice, Player target, Game game)
            throws NoValidChoiceException {
        Choice parent = choice.getParentChoice();
        choice.setMessage("Choose one:");
        choice.setIsDialog(true);

        Set<String> textOptions = new HashSet<>();
        textOptions.add("+2 Money");

        choice.setTextOptions(textOptions);

        return choice;
    }

    @Override
    public Effect getEffect(Player player, Game game) {
        return new MinionEffect();
    }

    @Override
    public void play(Player player, Game game) {
        Turn turn = player.getCurrentTurn();
        turn.setActionPool(turn.getActionPool() + 2);
    }
}
