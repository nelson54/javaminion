package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.commands.CommandRepository;
import com.github.nelson54.dominion.commands.CommandType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommandService {

    private CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public List<Command> findCommandsForGame(Game game) {
        return this.commandRepository.findByGameIdOrderByTimeAsc(game.getId())
                .orElse(new ArrayList<>());
    }

    public Command save(Command command) {
        return this.commandRepository.save(command);
    }

    public Game applyCommand(Game game, Command command) {
        Turn turn = game.getTurn();
        Card card = game.getAllCards().get(command.cardId);
        Player player = game.getPlayers().get(command.accountId);

        if(command.type.equals(CommandType.ACTION_COMMAND)) {
            turn.playCard((ActionCard) card, player, game);
        } else if(command.type.equals(CommandType.BUY_COMMAND)) {
            turn.purchaseCardForPlayer(card, player);
        } else if(command.type.equals(CommandType.END_PHASE)) {
            turn.endPhase();
        } else if(command.type.equals(CommandType.END_TURN)) {

        }

        if(command.getId() == null) {
            save(command);
        }

        return game;
    }

    public void deleteAll() {
        commandRepository.deleteAll();
    }
}
