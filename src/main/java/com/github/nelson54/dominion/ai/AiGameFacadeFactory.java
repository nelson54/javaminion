package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.services.CommandService;
import com.github.nelson54.dominion.services.MatchService;
import org.springframework.stereotype.Component;

@Component
public class AiGameFacadeFactory {

    private MatchService matchService;

    public AiGameFacadeFactory(MatchService matchService) {
        this.matchService = matchService;
    }

    public AiGameFacade getAiGameFacade(Game game, Turn turn, Player player) {
        return new AiGameFacade(matchService, game, turn, player);
    }
}
