package com.github.nelson54.dominion.ai.decisions;

import com.github.nelson54.dominion.ai.AiGameFacade;

public class AiDecisionBuilder {

    private AiGameFacade facade;

    public static AiDecisionBuilder start(AiGameFacade facade) {
        return new AiDecisionBuilder(facade);
    }


    public BuyCardBuilder buyPreferences() {
        return BuyCardBuilder.create(this);
    }

    private AiDecisionBuilder(AiGameFacade facade) {
        this.facade = facade;
    }

    public AiGameFacade getFacade() {
        return facade;
    }

    private void setFacade(AiGameFacade facade) {
        this.facade = facade;
    }
}
