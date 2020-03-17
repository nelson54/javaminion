package com.github.nelson54.dominion.game.ai;


public abstract class AiStrategy {

    public abstract void actionPhase(AiGameFacade game);

    public abstract void buyPhase(AiGameFacade game);

    public abstract void choice(AiGameFacade game);

}
