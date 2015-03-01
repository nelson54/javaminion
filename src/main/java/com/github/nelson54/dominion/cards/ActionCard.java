package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;

/**
 * Created by dnelson on 2/28/2015.
 */
public abstract class ActionCard extends Card {

    ActionCard(){
        cardTypes.add(CardType.ACTION);
    }

    public abstract void apply(Player player, Game game);

}
