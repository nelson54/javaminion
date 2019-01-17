package com.github.nelson54.dominion.commands;

import javax.persistence.Id;

public class Command {

    @Id
    public String id;
    public Long gameId;
}
