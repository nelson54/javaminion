package com.github.nelson54.dominion.commands;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class Command {

    @Id
    public String id;
    public Long gameId;
    public Long cardId;
    public LocalDateTime time;
    public CommandType type = CommandType.PURCHASE_COMMAND;
}
