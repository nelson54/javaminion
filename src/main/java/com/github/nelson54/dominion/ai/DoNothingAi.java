package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.HashSet;
import java.util.Set;

import static com.github.nelson54.dominion.choices.OptionType.CARD;
import static com.github.nelson54.dominion.choices.OptionType.YES_OR_NO;

class DoNothingAi extends AiStrategy {
    @Override
    public void actionPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void choice(AiGameFacade game) {
        game.getChoice().ifPresent(
                c->handleChoice(game, c)
            );
    }

    void handleChoice(AiGameFacade game, Choice choice) {
        ChoiceResponse choiceResponse = new ChoiceResponse();
        choiceResponse.setChoice(choice.getId().toString());
        choiceResponse.setSource(choice.getTarget());

        if(!choice.isRequired()){
            choiceResponse.setDone(true);
        } else if (choice.getExpectedAnswerType().equals(YES_OR_NO)){
            choiceResponse.setYes(false);
        } else if (choice.getExpectedAnswerType().equals(CARD)){
            Set<String> choices = new HashSet<>();

            choice.getOptions().stream().findFirst().ifPresent(choices::add);

            choiceResponse.setChoices(choices);
        }

        game.respond(choiceResponse);
    }
}
