package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiGameFacade;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.services.CommandService;
import com.github.nelson54.dominion.services.MatchService;


public class AiPlayer extends Player {
    private CommandService commandService;
    private MatchService matchService;
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

    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public void onActionPhase() {
        aiStrategy.actionPhase(new AiGameFacade(matchService, getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onBuyPhase() {
        if(!this.getGame().getRebuilding()) {
            aiStrategy.buyPhase(new AiGameFacade(matchService, getGame(), getCurrentTurn(), this));
        }
    }

    @Override
    public void onChoice() {
        if(!this.getGame().getRebuilding()) {
            aiStrategy.choice(new AiGameFacade(matchService, getGame(), getCurrentTurn(), this));
        }
    }

    public void setAiStrategy(AiStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }
}
