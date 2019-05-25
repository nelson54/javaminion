package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.AiPlayerService;
import com.github.nelson54.dominion.services.PhaseAdvisor;
import com.github.nelson54.dominion.web.Application;
import com.github.nelson54.dominion.web.controllers.AccountController;
import com.github.nelson54.dominion.web.controllers.GameController;
import com.github.nelson54.dominion.web.controllers.MatchController;
import com.github.nelson54.dominion.web.controllers.PlayerController;
import com.github.nelson54.dominion.web.security.WebSecurityConfig;
import com.google.common.collect.Streams;
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

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


@SpringBootApplication

@ComponentScan(value = {
        "com.github.nelson54.dominion",
        "com.github.nelson54.dominion.services",
        "com.github.nelson54.dominion.ai"
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Application.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AccountService.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AccountController.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = GameController.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MatchController.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = PlayerController.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class),
})

@EntityScan("com.github.nelson54.dominion.persistence.entities")

@EnableJpaRepositories("com.github.nelson54.dominion.persistence")
public class AiSimulator implements CommandLineRunner {
    public ApplicationContext context;

    private AiPlayerService aiPlayerService;
    private GameFactory gameFactory;

    private PhaseAdvisor phaseAdvisor;

    private final int timesToRun = 1000;

    private int completed = 0;
    private int p1Wins = 0;
    private int p2Wins = 0;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AiSimulator.class);
        app.setAdditionalProfiles("simulation");
        app.run(args);
    }


    public AiSimulator() {
    }

    public void run(String[] args) {
        AiStrategy ai1 = AiStrategies.random();
        AiStrategy ai2 = AiStrategies.random();
        AiStrategy[] ais = {ai1, ai2};

        List<AiStrategy> aiStrategyList = Arrays.asList(ais);

        Game game;
        DateTime start = DateTime.now();
        DateTime end;

        p1Wins = 0;
        p2Wins = 0;

        completed = 0;

        while ( completed < timesToRun ){

            Match match = new Match(2, GameCards.ALL_CARDS.getGameCardSet());

            Stream<MatchParticipant> mps = aiPlayerService.random(2)
                    .stream()
                    .map(MatchParticipant::new);

            Streams.zip(mps, aiStrategyList.stream(), (mp, s) -> {
                mp.setAiStrategy(s);
                return mp;
            }).forEach(match::addParticipant);

            game = gameFactory.createGame(match);
            game.setReadOnly(true);
            game.nextPlayer();
            game.resetPastTurns();

            while(game.getWinningPlayer().isEmpty()) {
                phaseAdvisor.advise(game);
            }

            completed++;

            if(((AiPlayer)game.getWinningPlayer().get()).getAiStrategy().equals(ai1)){
                p1Wins++;
            } else if (((AiPlayer)game.getWinningPlayer().get()).getAiStrategy().equals(ai2)) {
                p2Wins++;
            } else {
                System.out.println("WTF!");
            }

        }
        end = DateTime.now();

        Duration duration = Duration.millis(end.getMillis() - start.getMillis());

        System.out.println("Ran " + timesToRun+ " simulations in " +duration.toString());
        System.out.println( ai1.getClass().toString() + " finished with " + p1Wins +"/"+timesToRun+ " wins.");
        System.out.println( ai2.getClass().toString() + " finished with " + p2Wins +"/"+timesToRun+ " wins.");

    }

    private void run() {

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

    @Inject
    public void setPhaseAdvisor(PhaseAdvisor phaseAdvisor) {
        this.phaseAdvisor = phaseAdvisor;
    }
}
