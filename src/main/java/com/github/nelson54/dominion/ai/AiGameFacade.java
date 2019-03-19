package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.services.CommandService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class AiGameFacade {

    private CommandService commandService;
    private Game game;
    private Player player;
    private Turn turn;

    public AiGameFacade(CommandService commandService, Game game, Turn turn, Player player) {
        this.commandService = commandService;
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

        turn.playCard(card, player, game);
    }

    public void buy(Card card) {
        turn.purchaseCardForPlayer(card, player);
    }

    void respond(ChoiceResponse choiceResponse) {
        game.getChoiceById(choiceResponse.getChoice())
                .ifPresent(choice -> choice.apply(choiceResponse, turn));
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
        turn.endPhase();
    }
}
