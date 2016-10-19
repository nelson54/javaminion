package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;

import java.util.Optional;

import static com.github.nelson54.dominion.ai.AiUtils.gainsToEndGame;
import static com.github.nelson54.dominion.ai.AiUtils.numberOfCardsByName;

public class BigSmithyAi extends DoNothingAi {
    @Override
    public void actionPhase(AiGameFacade game) {
        Optional<ActionCard> smithy = game.getHand().stream()
                .filter(c->c.getName().equals("Smithy"))
                .filter(c->c instanceof ActionCard)
                .map(c-> (ActionCard)c)
                .findFirst();

        if(smithy.isPresent() && game.getActions() > 0){
            game.play(smithy.get());
        } else {
            game.endPhase();
        }
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
                .pick("Silver").or()
                //.pick("Copper").when(gainsToEndGame <= 3)
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

}
