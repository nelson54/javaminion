package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.Player;

public class AiPlayer extends Player {

    AiStrategy aiStrategy;

    public AiPlayer() {
        super();
    }

    @Override
    public void onActionPhase() {
        aiStrategy.actionPhase(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onBuyPhase() {
        aiStrategy.buyPhase(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onChoice() {
        aiStrategy.choice(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    public void setAiStrategy(AiStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }
}
