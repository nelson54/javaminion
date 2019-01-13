package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.services.MatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/dominion")
public class GameController {


    private MatchService matchService;

    public GameController(MatchService matchService) {
        this.matchService = matchService;
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    RecommendedCards[] getRecomendedCards(){
        return RecommendedCards.values();
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(@PathVariable("gameId") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return game(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping("/games")
    Page<Game> games(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();

        Page<Game> page = new PageImpl<>(new ArrayList<>());

        if(user != null && user.getUsername() != null) {
            page = new PageImpl<>(new ArrayList<>(matchService.all()));
        }

        return page;
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @PathVariable("gameId")
            Long id
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Game game = matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        game.getTurn().endPhase();
        return game;
    }

    private Optional<Game> game(Long id) {
        return matchService.getGame(id);
    }
}