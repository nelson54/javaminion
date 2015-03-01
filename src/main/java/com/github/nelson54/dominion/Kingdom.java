package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.Card;
import com.google.common.collect.Multimap;

import java.util.Collection;

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

    public Multimap<String, Card> getCardMarket() {
        return cardMarket;
    }

    public void setCardMarket(Multimap<String, Card> cardMarket) {
        this.cardMarket = cardMarket;
    }
}
