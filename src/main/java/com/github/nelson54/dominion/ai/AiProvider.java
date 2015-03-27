package com.github.nelson54.dominion.ai;


public abstract class AiProvider {

    public abstract void actionPhase(AiGameFacade game);

    public abstract void buyPhase(AiGameFacade game);

    public abstract void choice(AiGameFacade game);

}
