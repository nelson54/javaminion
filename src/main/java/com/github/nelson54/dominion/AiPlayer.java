package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiGameFacade;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.services.CommandService;


public class AiPlayer extends Player {
    private CommandService commandService;
    private AiStrategy aiStrategy;

    public AiPlayer(Account account) {
        super(account);
    }

    public AiPlayer(Account account, AiStrategy aiStrategy) {
        super(account);
        this.aiStrategy = aiStrategy;
    }

    public void setCommandService(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void onActionPhase() {
        aiStrategy.actionPhase(new AiGameFacade(commandService, getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onBuyPhase() {
        aiStrategy.buyPhase(new AiGameFacade(commandService, getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onChoice() {
        aiStrategy.choice(new AiGameFacade(commandService, getGame(), getCurrentTurn(), this));
    }

    public void setAiStrategy(AiStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }
}
