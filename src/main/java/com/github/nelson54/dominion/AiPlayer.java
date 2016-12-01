package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiGameFacade;
import com.github.nelson54.dominion.ai.AiStrategy;


public class AiPlayer extends Player {

    private AiStrategy aiStrategy;

    public AiPlayer(User user) {
        super(user);
        user.setAi(true);
    }

    public AiPlayer(User user, AiStrategy aiStrategy) {
        super(user);
        user.setAi(true);
        this.aiStrategy = aiStrategy;
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
