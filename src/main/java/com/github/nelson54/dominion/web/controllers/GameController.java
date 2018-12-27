package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.UsersProvider;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.persistence.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@RestController
@RequestMapping("/dominion")
public class GameController {

    @Inject
    GameRepository gameRepository;

    @Inject
    GameProvider gameProvider;

    @Inject
    UsersProvider usersProvider;

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    RecommendedCards[] getRecomendedCards(){
        return RecommendedCards.values();
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(
            @PathVariable("gameId")
            String id
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return gameRepository.findOne(id).asGame();
    }

    @RequestMapping("/games")
    Page<Game> games(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = usersProvider.getUserById(authentication.getName());

        Page<Game> page = new PageImpl<>(new ArrayList<>());

        if(user != null && user.getId() != null) {
            page = new PageImpl<>(new ArrayList<>(gameProvider.all()));
        }

        return page;
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @PathVariable("gameId")
            String id
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Game game = gameProvider.getGameByUuid(id);
        game.getTurn().endPhase();
        return game;
    }
}