package com.github.nelson54.dominion.ai;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum AiProviders {

    DO_NOTHING_AI(new DoNothingAi());

    AiProvider aiProvider;

    AiProviders(AiProvider aiProvider){
        this.aiProvider = aiProvider;
    }

    public AiProvider getAiProvider() {
        return aiProvider;
    }

    public static AiProvider random(){
        return Arrays.asList(values()).stream()
                .map(AiProviders::getAiProvider)
                .unordered()
                .findFirst().get();
    }
}
