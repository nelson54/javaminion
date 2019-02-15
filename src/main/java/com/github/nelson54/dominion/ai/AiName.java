package com.github.nelson54.dominion.ai;

import java.util.*;
import java.util.stream.Collectors;

public enum AiName {
    Monique,
    Jamal,
    Susan,
    Ross,
    James,
    Tanisha,
    Cane,
    Abel,
    Krishna,
    Saanvi,
    Prishna;

    public static Set<AiName> random(int number){
        List<AiName> aiNames = Arrays.asList(AiName.values());
        Collections.shuffle(aiNames);

        return aiNames.stream()
                .limit(number)
                .collect(Collectors.toSet());
    }

    public static AiName random(){
        List<AiName> aiNames = Arrays.asList(AiName.values());
        Collections.shuffle(aiNames);

        return aiNames.stream().findFirst().get();
    }
}
