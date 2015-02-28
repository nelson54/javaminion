package com.github.nelson54.dominion.cards;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.exceptions.IncorrectPhaseException;
import com.github.nelson54.dominion.exceptions.InsufficientFundsException;
import com.google.common.collect.Multimap;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.exceptions.IncorrectPhaseException;
import com.github.nelson54.dominion.exceptions.InsufficientFundsException;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by dnelson on 2/26/2015.
 */

@JsonAutoDetect
public class Kingdom {

    @JsonProperty
    Multimap<String, Card> cardMarket;

    Collection<Card> getCardsByName(String name){
        return cardMarket.get(name);
    }

    public long getNumberOfRemainingCardsByName(String name){
        if(!cardMarket.containsKey(name))
            return 0;
        else
            return getCardsByName(name).size();
    }

    public Card purchaseCardForPlayer(Card card, Player player, Game game)
            throws IncorrectPhaseException, InsufficientFundsException {
        if(!game.getPhase().equals(Phase.BUY)) {
            throw new IncorrectPhaseException();
        }

        if(!canAffordCost(player, card.getCost())){
            throw new InsufficientFundsException();
        }

        long buys = player.getBuys();
        player.spendMoney(card.getCost().getMoney());
        player.setBuys(--buys);
        return giveCardToPlayer(card.getName(), player);

    }

    public boolean canAffordCost(Player player, Cost cost) {
        return player.getMoney() >= cost.getMoney();
    }

    public Card giveCardToPlayer(String name, Player player){
        Collection<Card> cards = getCardsByName(name);
        Optional<Card> purchasedCard;

        if(cards != null) {
            purchasedCard = cards.stream().filter(card -> card.getOwner() == null).findFirst();
            cards.remove(purchasedCard.get());
            purchasedCard.ifPresent(card -> card.setOwner(player));
            player.getDiscard().add(purchasedCard.get());
            return purchasedCard.get();
        } else {
            return null;
        }
    }
}
