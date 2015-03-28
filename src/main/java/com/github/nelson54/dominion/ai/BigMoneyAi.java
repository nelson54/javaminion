package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Optional;

import static com.github.nelson54.dominion.ai.AiUtils.gainsToEndGame;
import static com.github.nelson54.dominion.ai.AiUtils.getTotalMoney;
import static com.github.nelson54.dominion.choices.OptionType.CARD;
import static com.github.nelson54.dominion.choices.OptionType.YES_OR_NO;

public class BigMoneyAi extends AiStrategy {
    @Override
    public void actionPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        int gainsToEndGame = gainsToEndGame(game.getKingdom());
        int totalMoney = getTotalMoney(game.getAllCards());

        Optional<Card> card = AiDecisionBuilder.start(game)
                .buyPreferences()
                .pick("Province").when(totalMoney > 18).or()
                .pick("Duchy").when(gainsToEndGame <= 4).or()
                .pick("Estate").when(gainsToEndGame <= 2).or()
                .pick("Gold").or()
                .pick("Duchy").when(gainsToEndGame <= 6).or()
                .pick("Silver")
                .findFirstMatch();

        if(card.isPresent()){
            game.buy(card.get());
        } else {
            game.endPhase();
        }
    }

    @Override
    public void choice(AiGameFacade game) {
        game.getChoice().ifPresent(
                c->handleChoice(game, c)
            );
    }

    private void handleChoice(AiGameFacade game, Choice choice) {
        ChoiceResponse choiceResponse = new ChoiceResponse();
        choiceResponse.setChoice(choice.getId().toString());
        choiceResponse.setSource(choice.getTarget());

        if(!choice.isRequired()){
            choiceResponse.setDone(true);
        } else if (choice.getExpectedAnswerType().equals(YES_OR_NO)){
            choiceResponse.setYes(false);
        } else if (choice.getExpectedAnswerType().equals(CARD)){
            Card firstCard = choice.getCardOptions().stream().findFirst().get();
            choiceResponse.setCard(firstCard);
        }

        game.respond(choiceResponse);
    }
}
