package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.*;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.TreasureCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.exceptions.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.validation.constraints.NotNull;
import java.util.*;

import static com.github.nelson54.dominion.Phase.*;

public class Turn {

    String id;

    @JsonProperty
    Phase phase;

    @JsonBackReference(value = "game")
    private Game game;

    @JsonIgnore
    private Player player;

    @JsonIgnore
    private Set<Choice> resolvedChoices;

    @JsonProperty
    private List<Card> play;

    private Multimap<Long, Card> revealed;
    Set<Card> gained;
    Set<Card> bought;

    private long actionPool;
    private long moneyPool;
    private long buyPool;

    private Long playerId;

    public Turn() {
        revealed = ArrayListMultimap.create();
        resolvedChoices = new HashSet<>();
    }

    static Turn create(Player player) {
        Turn turn = new Turn();
        turn.setPlayerId(player.getId());
        turn.setGame(player.getGame());
        turn.setBuyPool(1);
        turn.setActionPool(1);
        turn.setMoneyPool(0);
        turn.setPhase(BUY);
        turn.setPlay(new LinkedList<>());
        turn.setPlayer(player);

        return turn;
    }

    public void endPhase() {
        switch (phase) {
            case END_OF_GAME:
            case WAITING_FOR_CHOICE:
                break;
            case BUY:
                if (game.isGameOver()) {
                    game.endGame();
                } else {
                    endTurn();
                }
                break;
            case ACTION:
            default:
                phase = BUY;
                game.log(player.getName() + " is now entering buy phase");
                break;
        }
    }

    public void endTurn() {
        phase = ACTION;
        player.resetForNextTurn(this);
        game.nextPlayer();
    }

    public Card playCard(@NotNull ActionCard card, @NotNull Player player, Game game) {
        game.log(" " + player.getName() + " played card " + card.getName());

        if ((phase != ACTION || !player.getId().equals(this.player.getId())) ||
                card.getOwner() != null && card.getOwner().getId().equals(player.getId())) {
            throw new IncorrectPhaseException();
        }

        if (actionPool == 0) {
            throw new InsufficientActionsException();
        }

        if (play.contains(card)) {
            throw new AlreadyPlayedException();
        }

        actionPool--;
        player.getHand().remove(card);
        getPlay().add(card);
        card.apply(player, game);

        if (game.getChoices().size() > 0) {
            setPhase(WAITING_FOR_CHOICE);
        }
        //else if ((actionPool == 0)
        //        || (Cards.ofType(player.getHand(), CardType.ACTION).size() == 0)) {
        //    endPhase();
        //}

        return card;
    }

    public Card purchaseCardForPlayer(@NotNull Card card, @NotNull Player player)
            throws IncorrectPhaseException, InsufficientFundsException {

        game.log((new StringBuffer())
                .append(" ")
                .append(player.getName())
                .append(" purchased card ")
                .append(card.getName())
                .toString());

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

        // if (buyPool == 0) {
        //     endPhase();
        // }

        return getGame().giveCardToPlayer(card.getName(), player);
    }

    public boolean canAffordCost(Cost cost) {
        return getMoney() >= cost.getMoney();
    }

    public boolean canAffordCard(Card card) {
        return canAffordCost(card.getCost());
    }

    boolean hasActionsInHand() {
        return player.getHand().stream()
                .filter(c -> c instanceof ActionCard)
                .count() > 0;
    }

    @JsonProperty("money")
    public Integer getMoney() {
        return (int) Cards.ofType(player.getHand(), CardType.TREASURE)
                .stream()
                .map(card -> (TreasureCard) card)
                .mapToLong(card -> (int) card.getMoneyValue())
                .sum() + (int) moneyPool;
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

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Multimap<Long, Card> getRevealed() {
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
        if (revealed != null ? !revealed.equals(turn.revealed) : turn.revealed != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phase.hashCode();
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
