package com.github.nelson54.dominion.cards.sets.base;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.base.Copper;
import com.github.nelson54.dominion.cards.Cost;

import java.util.Optional;


public class Moneylender extends ActionCard {

    public Moneylender(Long id) {
        super(id);
        byte moneyCost = 4;

        Cost cost = new Cost();
        cost.setMoney(moneyCost);
        setCost(cost);

        setName("Moneylender");
    }

    @Override
    public void apply(Player player, Game game) {
        Turn turn = game.getTurn();
        Copper copper;
        Optional<Copper> maybe = player.getHand()
                .stream()
                .filter(card -> card instanceof Copper)
                .map(card -> (Copper) card)
                .findFirst();

        copper = maybe.get();

        if (copper != null) {
            game.trashCard(copper);
            turn.setMoneyPool(turn.getMoneyPool() + 3);
        }
    }
}
