package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.RecommendedCards;

import java.util.ArrayList;
import java.util.List;

public class AiSimulator {

    GameFactory gameFactory;

    int timeout = 60*60*5;
    int timesToRun = 10;
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

        while ( completed < timesToRun ){
            if(game == null){
                game = gameFactory.createTwoPlayerAiGame(ai1, ai2, RecommendedCards.BIG_MONEY.getCards());
            } else if (game.getTurn().getPhase().equals(Phase.END_OF_GAME)){
                completed++;
                game = gameFactory.createTwoPlayerAiGame(ai1, ai2, RecommendedCards.BIG_MONEY.getCards());

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


                System.out.println(firstPlayer.getName()+" wins.");

                if(firstPlayer.getVictoryPoints() > secondPlayer.getVictoryPoints()){
                    p1Wins++;
                } else {
                    p2Wins++;
                }
            }
        }

        System.out.println("Ai 1 finished with " + p1Wins + " wins.");
        System.out.println("Ai 2 finished with " + p2Wins + " wins.");
    }

    private void play(Game game){

    }

    GameFactory getGameFactory() {
        GameFactory gameFactory = new GameFactory();
        KingdomFactory kingdomFactory = new KingdomFactory();
        gameFactory.setKingdomFactory(kingdomFactory);
        return gameFactory;
    }
}
