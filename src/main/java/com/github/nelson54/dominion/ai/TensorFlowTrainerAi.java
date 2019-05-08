package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.ai.decisions.AiDecisionBuilder;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Tensor;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.nelson54.dominion.ai.AiUtils.gainsToEndGame;
import static com.github.nelson54.dominion.cards.CardUtils.numberOfCardsByName;

public class TensorFlowTrainerAi extends DoNothingAi {

    TensorFlowGraphInterface tfgi;


    @Override
    public void actionPhase(AiGameFacade game) {

        List<String> actions = game.getHand().stream()
                .filter(c -> c.isType(CardType.ACTION))
                .map(Card::getName)
                .collect(Collectors.toList());


        if (actions.size() > 0 && game.getActions() > 0) {

            byte[][] matrix = new byte[actions.size()][];
            int i=0;
            for(String action : actions) {
                try {
                    matrix[i++] = action.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            };

            Tensor<String> tensor = tfgi.session.runner().fetch("play")
                    .feed("turnNumber", Tensor.<Long>create(game.getTurnNumber(), Long.class))
                    .feed("card", Tensor.<String>create(matrix, String.class))
                    .feed("y", Tensor.<Double>create(6.0, Double.class))
                    .run().get(0).expect(String.class);

            try {
                System.out.println(new String(tensor.bytesValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            game.endPhase();
        }
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        int provincesInSupply = game.getKingdom().getNumberOfRemainingCardsByName("Province");
        int gainsToEndGame = gainsToEndGame(game.getKingdom());
        int smithysInDeck = numberOfCardsByName(game.getAllMyCards(), "Smithy");
//
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
//
        if (card.isPresent() && game.getBuys() > 0) {
            game.buy(card.get());
        } else {
            game.endPhase();
        }
    }



    @Override
    public void choice(AiGameFacade game) {
        //game.getChoice().ifPresent(c -> handleChoice(game, c));
    }

    public void setTfgi(TensorFlowGraphInterface tfgi) {
        this.tfgi = tfgi;
    }
}
