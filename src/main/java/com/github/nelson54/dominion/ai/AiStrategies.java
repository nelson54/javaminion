package com.github.nelson54.dominion.ai;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum AiStrategies {

    //DO_NOTHING_AI(new DoNothingAi()),
    BIG_MONEY(new BigMoneyAi()),
    BIG_SMITHY(new BigSmithyAi()),
    BIG_HATE(new BigHateAi()),
    BIG_SMITHY_WITCH(new BigSmithyWitchAi());

    AiStrategy aiStrategy;

    AiStrategies(AiStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }

    public AiStrategy getAiStrategy() {
        return aiStrategy;
    }

    public static AiStrategy random() {
        List<AiStrategy> strategies = Arrays.stream(values())
                .map(AiStrategies::getAiStrategy)
                .collect(Collectors.toList());

        Collections.shuffle(strategies);

        return strategies.get(0);
    }
}
