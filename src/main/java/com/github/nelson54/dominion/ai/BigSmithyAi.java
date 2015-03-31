package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.Optional;

import static com.github.nelson54.dominion.ai.AiUtils.gainsToEndGame;
import static com.github.nelson54.dominion.ai.AiUtils.numberOfCardsByName;
import static com.github.nelson54.dominion.choices.OptionType.CARD;
import static com.github.nelson54.dominion.choices.OptionType.YES_OR_NO;

public class BigSmithyAi extends AiStrategy {
    @Override
    public void actionPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        int provincesInSupply = game.getKingdom().getNumberOfRemainingCardsByName("Province");
        int gainsToEndGame = gainsToEndGame(game.getKingdom());
        int smithysInDeck = numberOfCardsByName(game.getAllMyCards(), "Smithy");

        Optional<Card> card = AiDecisionBuilder.start(game)
                .buyPreferences()
                .pick("Province").when(provincesInSupply <= 6).or()
                .pick("Duchy").when(gainsToEndGame <= 5).or()
                .pick("Estate").when(gainsToEndGame <= 2).or()
                .pick("Gold").or()
                .pick("Smithy").when(smithysInDeck < 2, game.getAllMyCards().size() >= 16).or()
                .pick("Smithy").when(smithysInDeck < 1).or()
                .pick("Silver").or()
                .pick("Copper").when(gainsToEndGame <= 3)
                .findFirstMatch();

        if(card.isPresent() && game.getBuys() > 0){
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
