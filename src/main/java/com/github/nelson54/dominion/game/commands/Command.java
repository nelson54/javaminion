package com.github.nelson54.dominion.game.commands;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

public class Command {

    @Id
    public String id;

    public String accountId;
    public String gameId;
    public Long cardId;
    public String buyName;
    public LocalDateTime time;
    public ChoiceResponseEntity choiceResponse;
    public CommandType type;
    public Integer gameHash;

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
        this.gameHash = game.hashCode();
    }

    private Command(
            Game game,
            Player account,
            ChoiceResponse choiceResponse,
            CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.choiceResponse = ChoiceResponseEntity.ofChoiceResponse(choiceResponse);
        this.type = commandType;
        this.time = LocalDateTime.now();
        this.gameHash = game.hashCode();
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

    public static Command resign(Game game, Player player) {
        return new Command(game, player, CommandType.RESIGN);
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
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

    public ChoiceResponseEntity getChoiceResponse() {
        return choiceResponse;
    }

    public void setChoiceResponse(ChoiceResponseEntity choiceResponse) {
        this.choiceResponse = choiceResponse;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return id.equals(command.id) &&
                gameId.equals(command.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameId);
    }
}
