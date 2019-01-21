package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.services.CommandService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

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
