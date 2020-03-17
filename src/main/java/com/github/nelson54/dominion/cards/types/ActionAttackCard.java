package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.CardUtils;

public abstract class ActionAttackCard extends ActionCard {

    public ActionAttackCard(Long id) {
        super(id);
        cardTypes.add(CardType.ATTACK);
    }

    public void apply(Player player, Game game) {
        bonus(player, game);
        getOtherPlayers(player, game).stream()
                .filter(otherPlayer -> {
                    boolean hasMoat = CardUtils.numberOfCardsByName(otherPlayer.getHand(), "Moat") > 0;

                    if (hasMoat) {
                        game.log(otherPlayer.getName() + " reveals a Moat and skips the attack");
                    }

                    return !hasMoat;
                })
                .forEach(other -> attack(other, game));
    }

    protected abstract void attack(Player player, Game game);

    protected abstract void bonus(Player player, Game game);
}
