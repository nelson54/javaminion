package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.RecommendedCards;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

public class AiSimulator {

    GameFactory gameFactory;

    int timeout = 60*60*5;
    int timesToRun = 1000;
    int completed = 0;
    int p1Wins = 0;
    int p2Wins = 0;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        AiSimulator aiSimulator = new AiSimulator();
        AiStrategy aiStrategy1 = AiStrategies.BIG_MONEY.getAiStrategy();
        AiStrategy aiStrategy2 = AiStrategies.BIG_SMITHY.getAiStrategy();

        aiSimulator.exec(aiStrategy1, aiStrategy2);
    }

    AiSimulator(){
        gameFactory = getGameFactory();
    }

    private void exec(AiStrategy ai1, AiStrategy ai2) throws IllegalAccessException, InstantiationException {
        Game game =  null;
        DateTime start = DateTime.now();
        DateTime end;
        while ( completed < timesToRun ){
            if(game == null){
                game = gameFactory.createTwoPlayerAiGame(ai1, ai2, RecommendedCards.FIRST_GAME.getCards());
            } else if (game.getTurn().getPhase().equals(Phase.END_OF_GAME)){
                completed++;
                game = gameFactory.createTwoPlayerAiGame(ai1, ai2, RecommendedCards.FIRST_GAME.getCards());

                List<Player> players = new ArrayList<>(game.getPlayers().values());

                Player firstPlayer;
                Player secondPlayer;

                if( players.get(0).getName().equals("p1")){
                    firstPlayer = players.get(0);
                    secondPlayer = players.get(1);
                } else {
                    firstPlayer = players.get(1);
                    secondPlayer = players.get(0);
                }


                //System.out.println(firstPlayer.getName()+" wins.");

                if(firstPlayer.getVictoryPoints() > secondPlayer.getVictoryPoints()){
                    p1Wins++;
                } else {
                    p2Wins++;
                }
            }
        }
        end = DateTime.now();

        Duration duration = Duration.millis(end.getMillis() - start.getMillis());

        System.out.println("Ran " + timesToRun+ " simulations in " +duration.toString());
        System.out.println( ai1.getClass().toString() + " finished with " + p1Wins +"/"+timesToRun+ " wins.");
        System.out.println( ai2.getClass().toString() + " finished with " + p2Wins +"/"+timesToRun+ " wins.");
    }

    GameFactory getGameFactory() {
        GameFactory gameFactory = new GameFactory();
        KingdomFactory kingdomFactory = new KingdomFactory();
        gameFactory.setKingdomFactory(kingdomFactory);
        return gameFactory;
    }
}
