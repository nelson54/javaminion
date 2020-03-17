package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.game.Kingdom;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import com.google.common.collect.Multimap;

import java.util.Set;

public class AiUtils {

    static String province = "Province";

    static int gainsToEndGame(Kingdom kingdom) {
        Multimap<String, Card> market = kingdom.getCardMarket();
        if (market.containsKey(province)) {
            return market.get(province).size();
        }
        return 0;
    }

    static int getTotalMoney(Set<Card> cards) {
        return cards.stream()
                .filter(card -> card instanceof TreasureCard)
                .map(c -> (TreasureCard) c)
                .mapToInt(TreasureCard::getMoneyValue)
                .sum();
    }
}
