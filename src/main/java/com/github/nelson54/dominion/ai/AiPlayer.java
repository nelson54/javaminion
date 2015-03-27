package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.Player;

public class AiPlayer extends Player {

    AiProvider aiProvider;

    public AiPlayer() {
        super();
    }

    @Override
    public void onActionPhase() {
        aiProvider.actionPhase(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onBuyPhase() {
        aiProvider.buyPhase(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onChoice() {
        aiProvider.choice(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    public void setAiProvider(AiProvider aiProvider) {
        this.aiProvider = aiProvider;
    }
}
