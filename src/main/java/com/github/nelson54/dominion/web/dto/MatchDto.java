package com.github.nelson54.dominion.web.dto;

public class MatchDto {
    private byte numberOfHumanPlayers;
    private byte numberOfAiPlayers;
    private byte count;
    private String cards;


    public MatchDto() {
    }

    public byte getNumberOfHumanPlayers() {
        return numberOfHumanPlayers;
    }

    public void setNumberOfHumanPlayers(byte numberOfHumanPlayers) {
        this.numberOfHumanPlayers = numberOfHumanPlayers;
    }

    public byte getNumberOfAiPlayers() {
        return numberOfAiPlayers;
    }

    public void setNumberOfAiPlayers(byte numberOfAiPlayers) {
        this.numberOfAiPlayers = numberOfAiPlayers;
    }

    public byte getCount() {
        return count;
    }

    public void setCount(byte count) {
        this.count = count;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }
}
