package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.*;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.exceptions.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.nelson54.dominion.Phase.ACTION;
import static com.github.nelson54.dominion.Phase.BUY;
import static com.github.nelson54.dominion.Phase.WAITING_FOR_OPPONENT;

public class Turn {

    @JsonProperty
    Phase phase;

    @JsonBackReference
    Game game;

    @JsonIgnore
    Player player;

    @JsonIgnore
    Set<Choice> resolvedChoices;

    @JsonProperty
    List<Card> play;

    Multimap<String, Card> revealed;

    long actionPool;
    long moneyPool;
    long buyPool;

    private String playerId;

    public Turn() {
        revealed = ArrayListMultimap.create();

        resolvedChoices = new HashSet<>();
    }

    static Turn create(Player player){
        Turn turn = new Turn();
        turn.setPlayerId(player.getId().toString());
        turn.setGame(player.getGame());
        turn.setBuyPool(1);
        turn.setActionPool(1);
        turn.setMoneyPool(0);
        turn.setPhase(WAITING_FOR_OPPONENT);
        turn.setPlay(new ArrayList<>());
        turn.setPlayer(player);

        return turn;
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

    public Card playCard(ActionCard card, Player player, Game game) {

        game.log("Player  "+player.getName()+" played card " + card.getName());

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

        if(actionPool == 0 || Cards.cardsOfType(player.getHand(), ActionCard.class).size() == 0) {
            endPhase();
        } else {
            player.onActionPhase();
        }

        return card;
    }

    public Card purchaseCardForPlayer(Card card, Player player)
            throws IncorrectPhaseException, InsufficientFundsException {

        game.log("Player  "+player.getName()+" purchased card " + card.getName());

        if (!phase.equals(Phase.BUY) || !player.getId().equals(this.player.getId())) {
            throw new IncorrectPhaseException();
        }

        if (!canAffordCost(card.getCost())) {
            throw new InsufficientFundsException();
        }

        if (buyPool == 0) {
            throw new InsufficientBuysException();
        }

        spendMoney(card.getCost().getMoney());
        buyPool--;
        Card bought = getGame().giveCardToPlayer(card.getName(), player);

        if(buyPool == 0){
            endPhase();
        } else {
            player.onBuyPhase();
        }

        return bought;
    }

    public boolean canAffordCost(Cost cost) {
        return getMoney() >= cost.getMoney();
    }

    public boolean canAffordCard(Card card) {
        return canAffordCost(card.getCost());
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
                .mapToLong(card -> (int) card.getMoneyValue())
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Turn)) return false;

        Turn turn = (Turn) o;

        if (actionPool != turn.actionPool) return false;
        if (buyPool != turn.buyPool) return false;
        if (moneyPool != turn.moneyPool) return false;
        if (!game.equals(turn.game)) return false;
        if (phase != turn.phase) return false;
        if (!play.equals(turn.play)) return false;
        if (!player.equals(turn.player)) return false;
        if (!playerId.equals(turn.playerId)) return false;
        if (!resolvedChoices.equals(turn.resolvedChoices)) return false;
        if (revealed != null ? !revealed.equals(turn.revealed) : turn.revealed != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phase.hashCode();
        result = 31 * result + player.hashCode();
        result = 31 * result + resolvedChoices.hashCode();
        result = 31 * result + play.hashCode();
        result = 31 * result + (revealed != null ? revealed.hashCode() : 0);
        result = 31 * result + (int) (actionPool ^ (actionPool >>> 32));
        result = 31 * result + (int) (moneyPool ^ (moneyPool >>> 32));
        result = 31 * result + (int) (buyPool ^ (buyPool >>> 32));
        result = 31 * result + playerId.hashCode();
        return result;
    }
}
