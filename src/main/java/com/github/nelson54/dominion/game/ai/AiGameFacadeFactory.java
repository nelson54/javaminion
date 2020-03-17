package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.match.MatchService;
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
