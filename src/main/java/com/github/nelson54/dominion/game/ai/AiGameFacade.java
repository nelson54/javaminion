package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.game.*;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.match.MatchService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class AiGameFacade {

    private MatchService matchService;
    private Game game;
    private Player player;
    private Turn turn;

    public AiGameFacade(MatchService matchService, Game game, Turn turn, Player player) {
        this.matchService = matchService;
        this.game = game;
        this.player = player;
        this.turn = turn;
    }

    Set<Card> getHand() {
        return player.getHand();
    }

    Phase getPhase() {
        return turn.getPhase();
    }

    long getMoney() {
        return turn.getMoneyPool();
    }

    long getActions() {
        return turn.getActionPool();
    }

    long getBuys() {
        return turn.getBuyPool();
    }

    public Kingdom getKingdom() {
        return game.getKingdom();
    }

    Optional<Choice> getChoice() {
        Iterator<Choice> choices = player.getChoices().iterator();
        if (choices.hasNext()) {
            return Optional.of(choices.next());
        }
        return Optional.empty();
    }

    Set<Card> getAllMyCards() {
        return new HashSet<>(player.getAllCards().values());
    }

    public void play(ActionCard card) {
        Command command = Command.action(game, player, card);
        matchService.applyCommand(game, command);
    }

    public void buy(Card card) {
        Command command = Command.buy(game, player, card);
        matchService.applyCommand(game, command);
    }

    void respond(ChoiceResponse choiceResponse) {
        Command command = Command.choice(game, player, choiceResponse);
        matchService.applyCommand(game, command);
    }

    public Card getCardById(Long id) {
        return game.getAllCards().get(id);
    }

    public boolean canAffordCost(Cost cost) {
        return turn.canAffordCost(cost);
    }

    public boolean canAffordCard(Card card) {
        return canAffordCost(card.getCost());
    }

    boolean buyCardIf(String cardName, boolean... conditions) {
        return true;
    }

    void endPhase() {
        matchService.applyCommand(game, Command.endPhase(game, player));
    }

    public Long getTurnNumber() {
        return game.getTurnNumber();
    }
}
