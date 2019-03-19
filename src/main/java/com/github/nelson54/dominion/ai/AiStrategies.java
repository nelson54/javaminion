package com.github.nelson54.dominion.ai;

import java.util.Arrays;

public enum AiStrategies {

    //DO_NOTHING_AI(new DoNothingAi()),
    BIG_MONEY(new BigMoneyAi()),
    BIG_SMITHY(new BigSmithyAi());

    AiStrategy aiStrategy;

    AiStrategies(AiStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }

    public AiStrategy getAiStrategy() {
        return aiStrategy;
    }

    public static AiStrategy random() {
        return Arrays.asList(values()).stream()
                .map(AiStrategies::getAiStrategy)
                .unordered()
                .findFirst().get();
    }
}
