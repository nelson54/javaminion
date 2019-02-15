package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.services.CommandService;
import org.springframework.stereotype.Component;

@Component
public class AiGameFacadeFactory {

    private CommandService commandService;

    public AiGameFacadeFactory(CommandService commandService) {
        this.commandService = commandService;
    }

    public AiGameFacade getAiGameFacade(Game game, Turn turn, Player player) {
        return new AiGameFacade(commandService, game, turn, player);
    }
}
