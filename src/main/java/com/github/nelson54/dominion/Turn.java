package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.TreasureCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.exceptions.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.nelson54.dominion.Phase.ACTION;
import static com.github.nelson54.dominion.Phase.BUY;

public class Turn {

    @JsonProperty
    Phase phase;
    @JsonBackReference
    private
    Game game;
    @JsonIgnore
    private
    Player player;

    @JsonIgnore
    private
    Set<Choice> resolvedChoices;

    @JsonProperty
    private
    List<Card> play;

    Multimap<String, Card> revealed;

    private long actionPool;
    private long moneyPool;
    private long buyPool;

    private String playerId;

    public Turn() {
        revealed = ArrayListMultimap.create();

        resolvedChoices = new HashSet<>();
    }

    public void endPhase() {
        switch (phase) {
            case END_OF_GAME:
            case WAITING_FOR_CHOICE:
                break;
            case BUY:
                if(game.isGameOver()){
                    game.endGame();
                } else {
                    phase = ACTION;
                    player.resetForNextTurn(this);
                    game.nextPlayer();
                }
                break;
            case ACTION:
            default:
                phase = BUY;
                player.onBuyPhase();
                break;
        }
    }

    public void playCard(ActionCard card, Player player, Game game) {
        if (phase != ACTION || !player.getId().equals(this.player.getId())) {
            throw new IncorrectPhaseException();
        }

        if (actionPool == 0) {
            throw new InsufficientActionsException();
        }

        if (play.contains(card)){
            throw new AlreadyPlayedException();
        }

        actionPool--;
        player.getHand().remove(card);
        getPlay().add(card);
        card.apply(player, game);

        if(player.getCurrentTurn().getPhase().equals(ACTION)){
            player.onActionPhase();
        }
    }

    public Card purchaseCardForPlayer(Card card, Player player)
            throws IncorrectPhaseException, InsufficientFundsException {
        if (!phase.equals(Phase.BUY) || !player.getId().equals(this.player.getId())) {
            throw new IncorrectPhaseException();
        }

        if (!canAffordCost(player, card.getCost())) {
            throw new InsufficientFundsException();
        }

        if (buyPool == 0) {
            throw new InsufficientBuysException();
        }

        spendMoney(card.getCost().getMoney());
        buyPool--;
        return getGame().giveCardToPlayer(card.getName(), player);
    }

    boolean canAffordCost(Player player, Cost cost) {
        return getMoney() >= cost.getMoney();
    }

    boolean hasActionsInHand(){
        return player.getHand().stream()
                .filter(c-> c instanceof ActionCard)
                .count() > 0;
    }

    @JsonProperty("money")
    long getMoney() {
        return player.getHand().stream()
                .filter(card -> card instanceof TreasureCard)
                .map(card -> (TreasureCard) card)
                .mapToLong(card -> (int) card.getMoneyValue(player, game))
                .sum() + moneyPool;
    }

    public long addToMoneyPool(long money) {
        return moneyPool += money;
    }

    void spendMoney(long money) {
        moneyPool -= money;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<Card> getPlay() {
        return play;
    }

    public void setPlay(List<Card> play) {
        this.play = play;
    }

    public long getActionPool() {
        return actionPool;
    }

    public void setActionPool(long actionPool) {
        this.actionPool = actionPool;
    }

    public long getMoneyPool() {
        return moneyPool;
    }

    public void setMoneyPool(long moneyPool) {
        this.moneyPool = moneyPool;
    }

    public long getBuyPool() {
        return buyPool;
    }

    public void setBuyPool(long buyPool) {
        this.buyPool = buyPool;
    }

    public Set<Choice> getResolvedChoices() {
        return resolvedChoices;
    }

    public void setResolvedChoices(Set<Choice> resolvedChoices) {
        this.resolvedChoices = resolvedChoices;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Multimap<String, Card> getRevealed() {
        return revealed;
    }
}
