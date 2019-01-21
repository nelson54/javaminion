package com.github.nelson54.dominion.commands;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.Card;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class Command {

    @Id
    public String id;

    public Long accountId;
    public Long gameId;
    public Long cardId;
    public LocalDateTime time;
    public CommandType type;

    public Command(Game game, Player account, Card card, CommandType commandType) {
        this.gameId = game.getId();
        this.accountId = account.getId();
        this.cardId = card.getId();
        this.type = commandType;
        this.time = LocalDateTime.now();
    }

    public static Command buy(Game game, Player account, Card card) {
        return new Command(game, account, card, CommandType.BUY_COMMAND);
    }

    public static Command action(Game game, Player account, Card card) {
        return new Command(game, account, card, CommandType.ACTION_COMMAND);
    }

    public String getId() {
        return id;
    }
}
