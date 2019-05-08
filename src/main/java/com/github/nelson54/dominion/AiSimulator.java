package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.TensorFlowGraphInterface;
import com.github.nelson54.dominion.ai.TensorFlowTrainerAi;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.services.AiPlayerService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

@ComponentScan(value = {
        "com.github.nelson54.dominion",
        "com.github.nelson54.dominion.services",
        "com.github.nelson54.dominion.ai"
})

@EntityScan("com.github.nelson54.dominion.persistence.entities")

@EnableJpaRepositories("com.github.nelson54.dominion.persistence")
@EnableMongoRepositories("com.github.nelson54.dominion.commands")
public class AiSimulator implements CommandLineRunner {
    private AiPlayerService aiPlayerService;
    private GameFactory gameFactory;

    private final int timeout = 60*60*5;
    private final int timesToRun = 1000;

    private int completed = 0;
    private int p1Wins = 0;
    private int p2Wins = 0;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpringApplication app = new SpringApplication(AiSimulator.class);
        app.run(args);
    }


    public AiSimulator() {
    }

    public void run(String[] args) {

        TensorFlowTrainerAi ai1 = new TensorFlowTrainerAi();
        TensorFlowTrainerAi ai2 = new TensorFlowTrainerAi();
        TensorFlowGraphInterface tfgi = new TensorFlowGraphInterface();
        ai1.setTfgi(tfgi);
        ai2.setTfgi(tfgi);

        Game game =  null;
        DateTime start = DateTime.now();
        DateTime end;
        while ( completed < timesToRun ){
            if(game == null){
                Match match = new Match(2, GameCards.ALL_CARDS.getGameCardSet());

                aiPlayerService
                        .random(2)
                        .stream()
                        .map(MatchParticipant::new)
                        .forEach(match::addParticipant);

                game = gameFactory.createGame(match);
            } else if (game.getTurn().getPhase().equals(Phase.END_OF_GAME)){
                completed++;

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

    @Bean
    GameFactory getGameFactory(KingdomFactory kingdomFactory) {
        this.gameFactory = new GameFactory(kingdomFactory);
        return this.gameFactory;
    }

    public void setAiPlayerService(AiPlayerService aiPlayerService) {
        this.aiPlayerService = aiPlayerService;
    }
}
