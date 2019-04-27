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
    public String buyName;
    public LocalDateTime time;
    public ChoiceResponseMongo choiceResponse;
    public CommandType type;

    public Command(){}

    private Command(Game game, Player account, CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    private Command(Game game, Player account, Card card, CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.cardId = card.getId();
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    private Command(
            Game game,
            Player account,
            ChoiceResponse choiceResponse,
            CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.choiceResponse = ChoiceResponseMongo.ofChoiceResponse(choiceResponse);
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    public static Command buy(Game game, Player account, Card card) {
        Command command = new Command(game, account, card, CommandType.BUY_COMMAND);
        command.buyName = card.getName();
        return command;
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

    public ChoiceResponse getChoiceResponse(Game game) {
        return this.choiceResponse.toChoiceResponse(game);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ChoiceResponseMongo getChoiceResponse() {
        return choiceResponse;
    }

    public void setChoiceResponse(ChoiceResponseMongo choiceResponse) {
        this.choiceResponse = choiceResponse;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }
}
