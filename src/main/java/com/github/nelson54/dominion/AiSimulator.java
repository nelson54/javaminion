package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.services.AiPlayerService;
import com.github.nelson54.dominion.web.Application;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value= Application.class)
})

@EntityScan("com.github.nelson54.dominion.persistence.entities")

@EnableJpaRepositories("com.github.nelson54.dominion.persistence")
@EnableMongoRepositories("com.github.nelson54.dominion.commands")
public class AiSimulator implements CommandLineRunner {
    public ApplicationContext context;

    private AiPlayerService aiPlayerService;
    private GameFactory gameFactory;

    private final int timeout = 60*60*5;
    private final int timesToRun = 100;

    private int completed = 0;
    private int p1Wins = 0;
    private int p2Wins = 0;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AiSimulator.class);
        app.run(args);
    }


    public AiSimulator() {
    }

    public void run(String[] args) {
        for(int i=0; i < 10; i++){
            run();
        }

    }

    private void run() {
        AiStrategy ai1 = AiStrategies.random();
        AiStrategy ai2 = AiStrategies.random();

        Game game =  null;
        DateTime start = DateTime.now();
        DateTime end;

        p1Wins = 0;
        p2Wins = 0;

        completed = 0;
        while ( completed < timesToRun ){

            Match match = new Match(2, GameCards.ALL_CARDS.getGameCardSet());

            aiPlayerService
                    .random(2)
                    .stream()
                    .map(MatchParticipant::new)
                    .forEach(match::addParticipant);

            game = gameFactory.createGame(match);
            game.setReadOnly(true);
            game.nextPlayer();
            game.resetPastTurns();

            while(!game.getTurn().getPhase().equals(Phase.END_OF_GAME)) {
                Turn turn = game.getTurn();
                Player player = turn.getPlayer();
                try {
                    if (turn.getPhase().equals(Phase.BUY)) {
                        player.onBuyPhase();
                    } else if (turn.getPhase().equals(Phase.ACTION)) {
                        player.onActionPhase();
                    } else if (turn.getPhase().equals(Phase.WAITING_FOR_CHOICE)) {
                        game.getChoices().forEach((choice) -> choice.getTarget().onChoice());
                    }
                } catch(RuntimeException e) {
                    // Do Nothing
                }
            }


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
        end = DateTime.now();

        Duration duration = Duration.millis(end.getMillis() - start.getMillis());

        System.out.println("Ran " + timesToRun+ " simulations in " +duration.toString());
        System.out.println( ai1.getClass().toString() + " finished with " + p1Wins +"/"+timesToRun+ " wins.");
        System.out.println( ai2.getClass().toString() + " finished with " + p2Wins +"/"+timesToRun+ " wins.");
        //((ConfigurableApplicationContext) context).close();
    }

    @Bean
    GameFactory getGameFactory(KingdomFactory kingdomFactory) {
        this.gameFactory = new GameFactory(kingdomFactory);
        return this.gameFactory;
    }

    @Inject
    public void setAiPlayerService(AiPlayerService aiPlayerService) {
        this.aiPlayerService = aiPlayerService;
    }

    @Inject
    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
