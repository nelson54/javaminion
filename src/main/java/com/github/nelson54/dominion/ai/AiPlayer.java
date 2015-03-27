package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.Player;

public class AiPlayer extends Player {

    AiProvider aiProvider;

    public AiPlayer() {
        super();
    }

    @Override
    public void onStartOfActionPhase() {
        aiProvider.actionPhase(new AiGameFacade(getGame(), getCurrentTurn(), this));
    }

    @Override
    public void onStartOfBuyPhase() {

    }

    @Override
    public void onChoice() {

    }

    public void setAiProvider(AiProvider aiProvider) {
        this.aiProvider = aiProvider;
    }
}
