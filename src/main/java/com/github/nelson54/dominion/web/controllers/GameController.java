package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.UsersProvider;
import com.github.nelson54.dominion.cards.RecommendedCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion")
public class GameController {

    @Autowired
    GameProvider gameProvider;

    @Autowired
    UsersProvider usersProvider;

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    RecommendedCards[] getRecomendedCards(){
        return RecommendedCards.values();
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(
            @PathVariable("gameId")
            String id
    ) {
        return gameProvider.getGameByUuid(id);
    }

    @RequestMapping("/")
    Page<String> games(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = usersProvider.getUserById(authentication.getName());

        List<String> gameIds = gameProvider.getGamesForPlayer(user.getId())
                .stream()
                .map((game)->game.getId().toString())
                .collect(Collectors.toList());

        return new PageImpl<>(gameIds);
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    String endPhase(
            @PathVariable("gameId")
            String id
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Game game = gameProvider.getGameByUuid(id);
        game.getTurn().endPhase();
        return "redirect: /dominion/"+id+"/"+authentication.getName()+"/next-phase";
    }

    //void join


}