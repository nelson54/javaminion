package com.github.nelson54.dominion.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.game.commands.CommandService;
import org.springframework.stereotype.Service;

@Service
public class PhaseAdvisor {

    private final CommandService commandService;

    public PhaseAdvisor(CommandService commandService) {
        this.commandService = commandService;
    }

    public void advise(Game game) {
        makeCallsToAI(game);

        skipEmptyPhases(game);

    }

    private void makeCallsToAI(Game game) {
        Turn turn = game.getTurn();
        Player player = turn.getPlayer();

        while (!game.isGameOver() && (player.getAccount().getAi() || hasAiChoices(game))) {
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
    }

    private void skipEmptyPhases(Game game) {
        Turn turn = game.getTurn();
        Player player = turn.getPlayer();

        if(commandService != null) {
            if(turn.getPhase().equals(Phase.ACTION) && turn.getActionPool() == 0L) {
                apply(game, Command.endPhase(game, player));
            }

            if(turn.getPhase().equals(Phase.ACTION) && hasNoActionCardsInHand(player)) {
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

    private boolean hasAiChoices(Game game) {
        return game.getChoices().stream()
                .anyMatch((choice) -> choice.getTarget().getAccount().getAi());
    }

    private boolean hasNoActionCardsInHand(Player player) {
        return player.getHand().stream()
                .noneMatch((card -> card.isType(CardType.ACTION)));
    }

    private void apply(Game game, Command command) {
        try {
            commandService.applyCommand(game, command);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
