package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.google.common.collect.Multimap;
import org.jboss.logging.Logger;
import org.joda.time.DateTime;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.nelson54.dominion.Phase.*;


public class Game {

    private static final Logger logger = Logger.getLogger(Game.class);

    @JsonProperty
    private List<String> logs;

    @JsonProperty
    private Long id;

    @JsonIgnore
    RandomSeed seed;

    @JsonProperty
    private Kingdom kingdom;

    @JsonIgnore
    private Set<Player> turnOrder;

    @JsonIgnore
    private Map<Long, Card> allCards;

    @JsonProperty
    private Set<Turn> pastTurns;

    @JsonProperty
    private Set<Card> trash;

    @JsonIgnore
    Iterator<Player> turnerator;

    @JsonProperty
    private Map<Long, Player> players;

    @JsonProperty
    private boolean gameOver;

    @JsonProperty
    private Turn turn;

    public Game(Long id, Long seed) {
        this.id = id;
        this.seed = RandomSeed.create(seed);
        players = new HashMap<>();
        pastTurns = new LinkedHashSet<>();
        allCards = new HashMap<>();
        trash = new HashSet<>();
        logs = new ArrayList<>();
    }

    public Game(Long seed) {
        this.seed = RandomSeed.create(seed);
        pastTurns = new LinkedHashSet<>();
        allCards = new HashMap<>();
        trash = new HashSet<>();
        logs = new ArrayList<>();
    }

    Player nextPlayer() {

        if (turnerator == null || !turnerator.hasNext()) {
            turnerator = turnOrder.iterator();
        }

        if (turn != null) {
            clearAllChoices();
        }

        if (isGameOver()) {
            turn.phase = END_OF_GAME;
            gameOver = true;
            return null;
        }

        Player nextPlayer = turnerator.next();
        pastTurns.add(turn);
        turn = nextPlayer.getCurrentTurn();

        if (turn.hasActionsInHand()) {
            turn.setPhase(ACTION);
            logger.info("Now entering action phase for Player["+nextPlayer.getId()+"] "+nextPlayer.getName());
            nextPlayer.onActionPhase();
        } else {
            turn.setPhase(BUY);
            logger.info("Now entering buy phase for Player["+nextPlayer.getId()+"] "+nextPlayer.getName());
            nextPlayer.onBuyPhase();
        }

        return nextPlayer;
    }

    private void clearAllChoices() {
        for (Player player : players.values()) {
            turn.getResolvedChoices().addAll(player.getChoices());
            player.getChoices().clear();
        }
    }

    boolean isGameOver() {
        return kingdom.getNumberOfRemainingCardsByName("Province") == 0;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Card giveCardToPlayer(String name, Player player) {
        Collection<Card> cards = kingdom.getCardsByName(name);
        Optional<Card> purchasedCard;

        if (cards != null) {
            purchasedCard = cards.stream().filter(card -> card.getOwner() == null).findFirst();
            purchasedCard.ifPresent(cards::remove);
            purchasedCard.ifPresent(card -> card.setOwner(player));
            purchasedCard.ifPresent(card -> player.getDiscard().add(card));
            purchasedCard.ifPresent(card -> card.setOwner(player));
            return purchasedCard.get();
        } else {
            return null;
        }
    }

    public Optional<Choice> getChoiceById(String id) {
        Optional<Choice> optChoice = Optional.empty();

        for (Choice choice : getChoices()) {
            if (choice.getId().toString().equals(id)) {

                optChoice = Optional.of(choice);
                break;
            }
        }

        return optChoice;
    }

    public Set<Choice> getChoices() {
        return players.values().stream()
                .flatMap(p -> p.getChoices().stream())
                .collect(Collectors.toSet());
    }

    public void addChoice(Choice choice) {
        turn.phase = Phase.WAITING_FOR_CHOICE;
        choice.getTarget().addChoice(choice);
        choice.getTarget().onChoice();
    }

    void endGame() {
        players.values().stream()
                .map(Player::getCurrentTurn)
                .forEach(turn -> turn.setPhase(END_OF_GAME));
    }

    public Long getId() {
        return id;
    }

    public Map<Long, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Long, Player> players) {
        this.players = players;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        Multimap<String, Card> market = kingdom.getCardMarket();
        Set<Card> allCards = new HashSet<>();
        market.keySet().stream()
                .map(market::get)
                .forEach(allCards::addAll);

        allCards.stream()
                .forEach(card -> this.allCards.put(card.getId(), card));

        this.kingdom = kingdom;
    }

    Set<Player> getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(Set<Player> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void trashCard(Card card) {
        Player player = card.getOwner();
        player.getCurrentTurn().getPlay().remove(card);
        player.getDiscard().remove(card);
        player.getDeck().remove(card);
        player.getHand().remove(card);

        card.setOwner(null);

        trash.add(card);
    }

    public void log(String string){
        logger.info(string);
        logs.add(DateTime.now().toString() + ": " + string);
    }

    void revealCard(Player player, Card card){
        boolean isCardInHand = player.getHand().contains(card);
        String fromLocation = (isCardInHand ? " from hand." : " from deck.");


        logs.add(player.getName()+ " revealed card " + card.getName() + fromLocation);
    }

    public void revealCardsFromHand (Player player, Set<Card> cards) {
        StringJoiner sj = new StringJoiner(", ");
        cards.forEach(card -> sj.add(card.getName()));
        logs.add(player.getName()+ " revealed cards " + sj.toString() + " from hand.");
    }

    public Map<Long, Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(Map<Long, Card> allCards) {
        this.allCards = allCards;
    }

    public Set<Card> getTrash() {
        return trash;
    }

    public void setTurnerator(Iterator<Player> turnerator) {
        this.turnerator = turnerator;
    }

    @JsonProperty
    @Override
    public int hashCode() {
        int result = (0);
        result = 31 * result + kingdom.hashCode();
        //result = 31 * result + turnOrder.hashCode();
        result = 31 * result + allCards.hashCode();
        result = 31 * result + pastTurns.hashCode();
        result = 31 * result + trash.hashCode();
        result = 31 * result + players.hashCode();
        result = 31 * result + (gameOver ? 1 : 0);
        if(turn != null && turn.getPhase() != null) {
            result = 31 * result + turn.getPhase().hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (gameOver != game.gameOver) return false;
        if (!allCards.equals(game.allCards)) return false;
        if (!id.equals(game.id)) return false;
        if (!kingdom.equals(game.kingdom)) return false;
        if (!pastTurns.equals(game.pastTurns)) return false;
        if (!players.equals(game.players)) return false;
        if (!trash.equals(game.trash)) return false;
        return turn.equals(game.turn);

    }

    public boolean getGameOver() {
        return gameOver;
    }
}
