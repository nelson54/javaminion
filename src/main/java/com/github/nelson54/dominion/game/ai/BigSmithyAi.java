package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.game.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.types.Card;

import java.util.Optional;

import static com.github.nelson54.dominion.game.ai.AiUtils.gainsToEndGame;
import static com.github.nelson54.dominion.cards.CardUtils.numberOfCardsByName;

public class BigSmithyAi extends DoNothingAi {
    @Override
    public void actionPhase(AiGameFacade game) {
        if (playIfFound(game, "Smithy")) {
            return;
        }



        if (playIfFound(game, "Village")) {
            return;
        }

        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        int gainsToEndGame = gainsToEndGame(game.getKingdom());
        int smithysInDeck = numberOfCardsByName(game.getAllMyCards(), "Smithy");
        int villagesInDeck = numberOfCardsByName(game.getAllMyCards(), "Village");

        Optional<Card> card = AiDecisionBuilder.start(game)
                .buyPreferences()
                .pick("Province").or()
                .pick("Duchy").when(gainsToEndGame <= 5).or()
                .pick("Estate").when(gainsToEndGame <= 2).or()
                .pick("Gold").or()
                .pick("Smithy").when(smithysInDeck < 2).or()//, game.getAllMyCards().size() >= 16).or()
                .pick("Village").when(villagesInDeck < 2).or()
                .pick("Silver").or()
                .findFirstMatch();

        if (card.isPresent() && game.getBuys() > 0) {
            game.buy(card.get());
        } else {
            game.endPhase();
        }
    }



    @Override
    public void choice(AiGameFacade game) {
        game.getChoice().ifPresent(c -> handleChoice(game, c));
    }

}
