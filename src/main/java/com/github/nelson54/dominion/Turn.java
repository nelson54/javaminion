package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.TreasureCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.exceptions.IncorrectPhaseException;
import com.github.nelson54.dominion.exceptions.InsufficientActionsException;
import com.github.nelson54.dominion.exceptions.InsufficientBuysException;
import com.github.nelson54.dominion.exceptions.InsufficientFundsException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.github.nelson54.dominion.Phase.ACTION;
import static com.github.nelson54.dominion.Phase.BUY;

public class Turn {

    @JsonBackReference
    Game game;
    @JsonIgnore
    Player player;
    @JsonProperty
    Phase phase;

    @JsonProperty
    Set<Choice> unresolvedChoices;
    @JsonIgnore
    Set<Choice> resolvedChoices;


    @JsonProperty
    Set<Card> play;

    long actionPool;
    long moneyPool;
    long buyPool;

    String playerId;

    public Turn() {
        unresolvedChoices = new HashSet<>();
        resolvedChoices = new HashSet<>();
    }

    public void endPhase(){
        switch(phase){
            case WAITING_FOR_EFFECTS:
                break;
            case BUY:
                phase = ACTION;
                player.resetForNextTurn(this);
                game.nextPlayer();
                break;
            case ACTION:
            default:
                phase = BUY;
                break;
        }
    }

    public void playCard(ActionCard card, Player player, Game game){
        if(phase != ACTION || !player.getId().equals(this.player.getId())){
            throw new IncorrectPhaseException();
        }

        if(actionPool == 0) {
            throw new InsufficientActionsException();
        }

        actionPool--;
        player.getHand();
        getPlay().add(card);
        card.apply(player, game);
    }

    public Card purchaseCardForPlayer(Card card, Player player, Game game)
            throws IncorrectPhaseException, InsufficientFundsException {
        if(!phase.equals(Phase.BUY) || !player.getId().equals(this.player.getId())) {
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

    public void addChoice(Choice<?> choice){
        phase = Phase.WAITING_FOR_CHOICE;
        unresolvedChoices.add(choice);
    }

    public Optional<Choice>  getUnresolvedChoiceById(String id){
        Optional<Choice> optChoice = Optional.empty();

        for(Choice choice : unresolvedChoices){
            if(choice.getId().toString().equals(id)){

                optChoice = Optional.of(choice);
                break;
            }
        }

        return optChoice;
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

    public Set<Choice> getUnresolvedChoices() {
        return unresolvedChoices;
    }

    public void setUnresolvedChoices(Set<Choice> unresolvedChoices) {
        this.unresolvedChoices = unresolvedChoices;
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
}
