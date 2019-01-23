package com.github.nelson54.dominion.commands;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class Command {

    @Id
    public String id;

    public Long accountId;
    public Long gameId;
    public Long cardId;
    public LocalDateTime time;
    public ChoiceResponseMongo choiceResponse;
    public CommandType type;

    public Command(){}

    public Command(Game game, Player account, CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    public Command(Game game, Player account, Card card, CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.cardId = card.getId();
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    public Command(Game game, Player account, ChoiceResponse choiceResponse, CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.choiceResponse = ChoiceResponseMongo.ofChoiceResponse(choiceResponse);
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    public static Command buy(Game game, Player account, Card card) {
        return new Command(game, account, card, CommandType.BUY_COMMAND);
    }

    public static Command action(Game game, Player account, Card card) {
        return new Command(game, account, card, CommandType.ACTION_COMMAND);
    }

    public static Command choice(Game game, Player player, ChoiceResponse choiceResponse) {
        return new Command(game, player, choiceResponse, CommandType.CHOICE_RESPONSE);
    }

    public static Command endPhase(Game game, Player player) {
        return new Command(game, player, CommandType.END_PHASE);
    }

    public static Command endTurn(Game game, Player player) {
        return new Command(game, player, CommandType.END_TURN);
    }

    public String getId() {
        return id;
    }

    public ChoiceResponse getChoiceResponse(Game game) {
        return this.choiceResponse.toChoiceResponse(game);
    }
}
