package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.UsersProvider;
import com.github.nelson54.dominion.ai.AiName;
import com.github.nelson54.dominion.web.gamebuilder.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion")
public class MatchController {

    @Autowired
    UsersProvider usersProvider;

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
    void createMatch(
            @RequestBody
            com.github.nelson54.dominion.web.gamebuilder.Game game
    ) throws InstantiationException, IllegalAccessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = usersProvider.getUserById(authentication.getName());

        Player player = new Player(user.getId());
        player.setName(user.getName());
        player.setAi(false);

        game.getPlayers().add(player);

        game.setId(UUID.randomUUID().toString());
        gameProvider.getMatching().put(game.getId(), game);

        fillInAiPlayers(game);

        join(game, user);
    }

    private void fillInAiPlayers(com.github.nelson54.dominion.web.gamebuilder.Game game) {
        Iterator<AiName> names = AiName.random(game.numberOfAiPlayers()).iterator();

        for(Player player : game.getPlayers()){
            if(player.isAi()){
                AiName name = names.next();
                player.setId(UUID.randomUUID().toString());
                player.setName(name.toString());
            }
        }
    }

    @RequestMapping(value="/matches", method=RequestMethod.PATCH)
    void join(String gameId) throws InstantiationException, IllegalAccessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = usersProvider.getUserById(authentication.getName());

        com.github.nelson54.dominion.web.gamebuilder.Game game = gameProvider.getMatching().get(gameId);

        join(game, user);

    }

    void join(com.github.nelson54.dominion.web.gamebuilder.Game game, User user) throws InstantiationException, IllegalAccessException {

        if(game.hasRemainingPlayers()){
            user.getGames().add(game.getId());
            game.findUnsetHumanPlayer().ifPresent(p -> {
                p.setId(user.getId());
                p.setName(user.getName());
            });
        }


        gameProvider.createGameBySet(game);


    }
}
