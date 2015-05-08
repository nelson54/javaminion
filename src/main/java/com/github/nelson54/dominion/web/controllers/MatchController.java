package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion")
public class MatchController {

    @Autowired
    GameProvider gameProvider;

    @RequestMapping(value="/matches", method= RequestMethod.GET)
    Page<String> matches(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String playerId = authentication.getName();

        List<String> gameIds = gameProvider.getMatching().values().stream()
                .filter(game -> game.getPlayers().stream().noneMatch(p -> p.getId().equals(playerId)))
                .map(com.github.nelson54.dominion.web.gamebuilder.Game::getId)
                .collect(Collectors.toList());

        return new PageImpl<>(gameIds);
    }

    @RequestMapping(value = "/matches", method = RequestMethod.POST)
    void createMatch(com.github.nelson54.dominion.web.gamebuilder.Game game){
        game.setId(UUID.randomUUID().toString());
        gameProvider.getMatching().put(game.getId(), game);
    }

    @RequestMapping(value="/matches", method=RequestMethod.PATCH)
    void join(String gameId) throws InstantiationException, IllegalAccessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        com.github.nelson54.dominion.web.gamebuilder.Game game =gameProvider.getMatching().get(gameId);

        if(game.hasRemainingPlayers()){
            game.findUnsetPlayer().ifPresent(p -> p.setId(authentication.getName()));
        } else {
            gameProvider.createAiGameBySet(game.getCardSet(), game);
        }
    }
}
