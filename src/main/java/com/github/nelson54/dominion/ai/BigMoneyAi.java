package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.types.Card;

import java.util.Optional;

import static com.github.nelson54.dominion.ai.AiUtils.gainsToEndGame;
import static com.github.nelson54.dominion.ai.AiUtils.getTotalMoney;

class BigMoneyAi extends DoNothingAi {
    @Override
    public void actionPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        int gainsToEndGame = gainsToEndGame(game.getKingdom());
        int totalMoney = getTotalMoney(game.getAllMyCards());
        Optional<Card> card = AiDecisionBuilder.start(game)
                .buyPreferences()
                .pick("Province").when(totalMoney > 18).or()
                .pick("Duchy").when(gainsToEndGame <= 4).or()
                .pick("Estate").when(gainsToEndGame <= 2).or()
                .pick("Gold").or()
                .pick("Duchy").when(gainsToEndGame <= 6).or()
                .pick("Silver")
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
