package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.TreasureCard;
import com.github.nelson54.dominion.exceptions.IncorrectPhaseException;
import com.github.nelson54.dominion.exceptions.InsufficientActionsException;
import com.github.nelson54.dominion.exceptions.InsufficientBuysException;
import com.github.nelson54.dominion.exceptions.InsufficientFundsException;

import java.util.Set;

import static com.github.nelson54.dominion.Phase.ACTION;
import static com.github.nelson54.dominion.Phase.BUY;

/**
 * Created by dnelson on 2/28/2015.
 */
public class Turn {

    @JsonBackReference
    Game game;
    Player player;
    Phase phase;

    Set<Card> play;

    long actionPool;
    long moneyPool;
    long buyPool;


    public void endPhase(){
        switch(phase){
            case BUY:
                phase = ACTION;
                player.resetForTurn();
                game.nextPlayer();
                break;
            case ACTION:
            default:
                phase = BUY;
                break;
        }
    }


    public void playCard(ActionCard card, Player player, Game game){
        if(phase != ACTION){
            throw new IncorrectPhaseException();
        }

        if(actionPool == 0) {
            throw new InsufficientActionsException();
        }

        actionPool--;
        card.apply(player, game);
    }

    public Card purchaseCardForPlayer(Card card, Player player, Game game)
            throws IncorrectPhaseException, InsufficientFundsException {
        if(!phase.equals(Phase.BUY)) {
            throw new IncorrectPhaseException();
        }

        if(!canAffordCost(player, card.getCost())){
            throw new InsufficientFundsException();
        }

        if(buyPool == 0){
            throw new InsufficientBuysException();
        }

        spendMoney(card.getCost().getMoney());
        buyPool--;
        return game.giveCardToPlayer(card.getName(), player);
    }

    public boolean canAffordCost(Player player, Cost cost) {
        return getMoney() >= cost.getMoney();
    }

    @JsonProperty("money")
    public long getMoney() {
        return player.getHand().stream()
                .filter(card -> card instanceof TreasureCard)
                .map(card -> (TreasureCard)card)
                .mapToLong(card -> (int)card.getMoneyValue(player, game))
                .sum() + moneyPool;
    }

    public long addToMoneyPool(long money){
        return moneyPool += money;
    }

    public void spendMoney(long money) {
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

    public Set<Card> getPlay() {
        return play;
    }

    public void setPlay(Set<Card> play) {
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
}
