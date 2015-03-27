package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;

public class DoNothingAi extends AiProvider {
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
        Choice choice = game.getChoice();
        ChoiceResponse choiceResponse = new ChoiceResponse();
        choiceResponse.setChoice(choice.getId().toString());
        choiceResponse.setSource(choice.getTarget());

        if(!choice.isRequired()){
            choiceResponse.setDone(true);
        } else if (choice.getExpectedAnswerType().equals(OptionType.YES_OR_NO)){
            choiceResponse.setYes(false);
        } else if (choice.getExpectedAnswerType().equals(OptionType.CARD)){
            Card firstCard = choice.getCardOptions().stream().findFirst().get();
            choiceResponse.setCard(firstCard);
        }

        game.respond(choiceResponse);
    }
}
