package com.github.nelson54.dominion.cards;

/**
 * Created by dnelson on 2/26/2015.
 */
public class Cost {

    byte money = 0;
    byte potions = 0;

    public Cost() {
    }

    public byte getMoney() {
        return money;
    }

    public void setMoney(byte money) {
        this.money = money;
    }

    public byte getPotions() {
        return potions;
    }

    public void setPotions(byte potions) {
        this.potions = potions;
    }
}
