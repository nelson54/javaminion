package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.AiPlayer;
import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import com.github.nelson54.dominion.services.CommandService;
import com.google.common.collect.Multimap;

import java.util.Set;

public class AiUtils {

    static String province = "Province";

    static int gainsToEndGame(Kingdom kingdom){
        Multimap<String, Card> market = kingdom.getCardMarket();
        if(market.containsKey(province)){
            return market.get(province).size();
        }
        return 0;
    }

    static int getTotalMoney(Set<Card> cards){
        return cards.stream()
                .filter(card -> card instanceof TreasureCard)
                .map(c -> (TreasureCard) c)
                .mapToInt(TreasureCard::getMoneyValue)
                .sum();
    }

    static int numberOfCardsByName(Set<Card> cards, String name){
        return (int) cards.stream()
                .filter(c -> c.getName().equals(name))
                .count();
    }

    public static AiPlayer randomPlayer() {
        Account account = new Account(Long.valueOf("10"),  null, "ai@example.com", AiName.random().toString(), true);
        return new AiPlayer(account);
    }


}
