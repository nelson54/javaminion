package com.github.nelson54.dominion.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.commands.Command;
import org.springframework.stereotype.Service;

@Service
public class PhaseAdvisor {

    private final CommandService commandService;

    public PhaseAdvisor(CommandService commandService) {
        this.commandService = commandService;
    }

    public void advise(Game game) {
        Turn turn = game.getTurn();
        Player player = turn.getPlayer();

        boolean hasAiChoices = game.getChoices().stream()
                .anyMatch((choice) -> choice.getTarget().getAccount().getAi());

        while (player.getAccount().getAi() || hasAiChoices) {
            if (turn.getPhase().equals(Phase.BUY)) {
                player.onBuyPhase();
            } else if (turn.getPhase().equals(Phase.ACTION)) {
                player.onActionPhase();
            } else if (turn.getPhase().equals(Phase.WAITING_FOR_CHOICE)) {
                game.getChoices().forEach((choice) -> choice.getTarget().onChoice());
            }

            turn = game.getTurn();
            player = turn.getPlayer();
        }

        if(commandService != null) {
            if(turn.getPhase().equals(Phase.ACTION) && turn.getActionPool() == 0L) {
                apply(game, Command.endPhase(game, player));
            }

            long actionCardsInHand = player.getHand().stream()
                    .filter((card -> card.isType(CardType.ACTION)))
                    .count();

            if(turn.getPhase().equals(Phase.ACTION) && actionCardsInHand == 0L) {
                apply(game, Command.endPhase(game, player));
            }

            if(turn.getPhase().equals(Phase.BUY) && turn.getMoney() < 2) {
                apply(game, Command.endPhase(game, player));
            }

            if(turn.getPhase().equals(Phase.BUY) && turn.getBuyPool() == 0L) {
                apply(game, Command.endPhase(game, player));
            }
        }
    }

    private void apply(Game game, Command command) {
        try {
            commandService.applyCommand(game, command);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
