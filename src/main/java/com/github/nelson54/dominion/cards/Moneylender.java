package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;

import java.util.Optional;

/**
 * Created by dnelson on 2/28/2015.
 */
public class Moneylender extends ActionCard {

    public Moneylender() {
        super();
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
                .filter(card->card instanceof Copper)
                .map(card -> (Copper) card)
                .findFirst();

        copper = maybe.get();

        if(copper != null){
            game.trashCard(copper);
            turn.setMoneyPool(turn.getMoneyPool() + 3);
        }
    }
}
