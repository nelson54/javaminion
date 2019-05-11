package com.github.nelson54.dominion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.google.common.collect.Multimap;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.nelson54.dominion.Phase.ACTION;
import static com.github.nelson54.dominion.Phase.END_OF_GAME;


public class Game {

    @JsonProperty
    private LinkedHashSet<String> logs;
    @JsonProperty @JsonSerialize(using = ToStringSerializer.class) @JsonDeserialize(as = Long.class)
    private Long id;
    @JsonIgnore
    public RandomSeed seed;
    @JsonProperty
    private Kingdom kingdom;
    @JsonIgnore
    private Set<Player> turnOrder;
    @JsonIgnore
    private Map<Long, Card> allCards;
    @JsonProperty
    private LinkedHashSet<Card> trash;
    @JsonIgnore
    Iterator<Player> turnerator;
    @JsonProperty
    private Map<Long, Player> players;
    @JsonProperty
    private Turn turn;
    private Boolean rebuilding;
    private LocalDateTime commandTime;
    private Long turnNumber;
    private Player resign;

    private Boolean isReadOnly = false;

    public Game(Long id, Long seed) {
        this.id = id;
        this.seed = RandomSeed.create(seed);
        players = new HashMap<>();
        allCards = new HashMap<>();
        trash = new LinkedHashSet<>();
        logs = new LinkedHashSet<>();
        turnNumber = 1L;
        rebuilding = false;
    }

    public Game(Long seed) {
        this.seed = RandomSeed.create(seed);
        allCards = new HashMap<>();
        trash = new LinkedHashSet<>();
        logs = new LinkedHashSet<>();
        turnNumber = 1L;
        rebuilding = false;
    }

    public void ensureTurnerator() {
        if (turnerator == null || !turnerator.hasNext()) {
            turnerator = turnOrder.iterator();
        }
    }

    public Player nextPlayer() {
        logs.add("Starting Turn " + turnNumber++);
        ensureTurnerator();

        if (turn != null) {
            clearAllChoices();
        }

        if (isGameOver()) {
            turn.phase = END_OF_GAME;
            return null;
        }

        Player nextPlayer = turnerator.next();
        turn = nextPlayer.getCurrentTurn();


        turn.setPhase(ACTION);
        log(nextPlayer.getName() + " is now entering action phase");

        return nextPlayer;
    }

    private void clearAllChoices() {
        for (Player player : players.values()) {
            turn.getResolvedChoices().addAll(player.getChoices());
            player.getChoices().clear();
        }
    }

    @JsonProperty
    public boolean isGameOver() {
        return kingdom.getNumberOfRemainingCardsByName("Province") == 0 || this.resign != null;
    }

    public Card giveCardToPlayer(String name, Player player) {
        Collection<Card> cards = kingdom.getCardsByName(name);
        Optional<Card> purchasedCard;

        if (cards != null) {
            purchasedCard = cards.stream().findAny();
            purchasedCard.ifPresent(cards::remove);
            purchasedCard.ifPresent(card -> {
                cards.remove(card);
                card.setOwner(player);
                player.getDiscard().add(card);
                card.setOwner(player);
            });
            if(purchasedCard.isPresent()) {
                return purchasedCard.get();
            } return null;
        } else {
            return null;
        }
    }

    public Optional<Choice> getChoiceById(String id) {
        Optional<Choice> optChoice = Optional.empty();

        StringBuffer sb = new StringBuffer();
        sb.append("Looking for choice: ")
                .append(id)
                .append(" [ ");
        for (Choice choice : getChoices()) {
            sb.append(choice.getId()).append(", ");
            if (choice.getId().toString().equals(id)) {

                optChoice = Optional.of(choice);
                break;
            }
        }

        sb.append(" ]");

        return optChoice;
    }

    public LinkedHashSet<Choice> getChoices() {
        return players.values().stream()
                .flatMap(p -> p.getChoices().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void addChoice(Choice choice) {
        turn.phase = Phase.WAITING_FOR_CHOICE;
        choice.getTarget().addChoice(choice);
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

    public Comparator<Player> rankComparator() {
        Comparator<Player> c = (a, b) -> {
            if (a.getVictoryPoints() > b.getVictoryPoints()) {
                return 1;
            } else if (a.getVictoryPoints() < b.getVictoryPoints()) {
                return -1;
            } else if (a.getTotalMoney() > b.getTotalMoney()) {
                return 1;
            } else if (b.getTotalMoney() > a.getTotalMoney()) {
                return -1;
            }
            return 0;
        };

        return c.reversed();
    }

    public Optional<Player> getWinningPlayer() {

            return players.values()
                    .stream()
                    .filter((player) -> !player.equals(resign))
                    .min(rankComparator());

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

        log(player.getName()
                + " trashed " + card.getName() + ".");

        card.setOwner(null);

        trash.add(card);
    }

    public void log(String string) {
        LocalDateTime dateTime = commandTime;

        if(dateTime == null) {
            dateTime = LocalDateTime.now();
        }

        logs.add(dateTime + ":: " + string);
    }

    void revealCard(Player player, Card card) {
        boolean isCardInHand = player.getHand().contains(card);
        String fromLocation = (isCardInHand ? " from hand." : " from deck.");


        log(player.getName() + " revealed card " + card.getName() + fromLocation);
    }

    public void revealCardsFromHand(Player player, Set<Card> cards) {
        StringJoiner sj = new StringJoiner(", ");
        cards.forEach(card -> sj.add(card.getName()));
        log(player.getName() + " revealed cards " + sj.toString() + " from hand.");
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

    public Iterator<Player> getTurnerator() {
        return turnerator;
    }

    public void setCommandTime(LocalDateTime commandTime) {
        this.commandTime = commandTime;
    }

    public LocalDateTime getCommandTime() {
        return commandTime;
    }


    public Boolean getRebuilding() {
        return rebuilding;
    }

    public void setRebuilding(Boolean rebuilding) {
        this.rebuilding = rebuilding;
    }

    public void resetPastTurns() {
        logs.clear();
    }

    public void setResign(Player player) {
        resign = player;
        endGame();
    }

    public Long getTurnNumber() {
        return turnNumber;
    }

    public Boolean getReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        isReadOnly = readOnly;
    }

    @JsonIgnore
    @Override
    public int hashCode() {
        int result = (0);
        result = 31 * result + kingdom.hashCode();
        result = 31 * result + allCards.hashCode();
        result = 31 * result + trash.hashCode();
        result = 31 * result + players.hashCode();
        if (turn != null && turn.getPhase() != null) {
            result = 31 * result + turn.getPhase().hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;
        if (!allCards.equals(game.allCards)) return false;
        if (!id.equals(game.id)) return false;
        if (!kingdom.equals(game.kingdom)) return false;
        if (!players.equals(game.players)) return false;
        if (!trash.equals(game.trash)) return false;
        return turn.equals(game.turn);

    }

    public LinkedHashSet<String> getLogs() {
        return logs;
    }
}
