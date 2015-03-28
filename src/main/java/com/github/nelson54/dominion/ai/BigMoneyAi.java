package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;

import java.util.Optional;

public class BigMoneyAi extends AiStrategy {
    @Override
    public void actionPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        int gainsToEndGame = AiUtils.gainsToEndGame(game.getKingdom());
        int totalMoney = AiUtils.getTotalMoney(game.getAllCards());

        Optional<Card> card = AiDecisionBuilder.start(game)
                .buyPreferences()
                .buy("Province").when(totalMoney > 18).or()
                .buy("Duchy").when(gainsToEndGame <= 4).or()
                .buy("Estate").when(gainsToEndGame <= 2).or()
                .buy("Gold").or()
                .buy("Duchy").when(gainsToEndGame <= 6).or()
                .buy("Silver")
                .findFirstMatch();

        if(card.isPresent()){
            game.buy(card.get());
        } else {
            game.endPhase();
        }
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
