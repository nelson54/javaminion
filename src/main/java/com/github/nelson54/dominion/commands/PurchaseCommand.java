package com.github.nelson54.dominion.commands;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.cards.types.Card;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PurchaseCommand extends Command {
    public Account account;
    public Card card;
    public LocalDateTime time;
    public CommandType type = CommandType.PURCHASE_COMMAND;


}
