package com.github.nelson54.dominion.match;

public class MatchDto {
    private Long id;
    private Integer numberOfHumanPlayers;
    private Integer numberOfAiPlayers;
    private Integer count;
    private String cards;


    public MatchDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfHumanPlayers() {
        return numberOfHumanPlayers;
    }

    public void setNumberOfHumanPlayers(Integer numberOfHumanPlayers) {
        this.numberOfHumanPlayers = numberOfHumanPlayers;
    }

    public Integer getNumberOfAiPlayers() {
        return numberOfAiPlayers;
    }

    public void setNumberOfAiPlayers(Integer numberOfAiPlayers) {
        this.numberOfAiPlayers = numberOfAiPlayers;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }
}
