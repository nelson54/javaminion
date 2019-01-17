package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.persistence.CommandRepository;

import java.util.List;

public class CommandService {

    private CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public List<Command> findCommandsForGame(Game game) {
        return this.commandRepository.findByGameIdOrderByTimeAsc(game.getId());
    }

    public Command save(Command command) {
        return this.commandRepository.save(command);
    }
}
