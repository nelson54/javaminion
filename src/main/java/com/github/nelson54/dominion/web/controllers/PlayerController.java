package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.web.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dominion/{gameId}/{playerId}")
public class PlayerController {

    @Autowired
    GameProvider gameProvider;

    @RequestMapping("/shuffle")
    Game shuffle(
            @PathVariable("gameId")
            String gameId,
            @PathVariable("playerId")
            String playerId
    ) {
        Game game = gameProvider.getGameByUuid(gameId);
        Player player = game.getPlayers().get(playerId);

        player.shuffle();

        return game;
    }

    @RequestMapping("/draw")
    Game drawHand(
            @PathVariable("gameId")
            String gameId,
            @PathVariable("playerId")
            String playerId
    ) {
        Game game = gameProvider.getGameByUuid(gameId);
        Player player = game.getPlayers().get(playerId);

        player.drawHand();

        return game;
    }

    @RequestMapping("/discard")
    Game discardHand(
            @PathVariable("gameId")
            String gameId,
            @PathVariable("playerId")
            String playerId
    ) {
        Game game = gameProvider.getGameByUuid(gameId);
        Player player = game.getPlayers().get(playerId);

        player.discardHand();

        return game;
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
     Game purchase(
                    @PathVariable("gameId")
                    String gameId,
                    @PathVariable("playerId")
                    String playerId,
                    @RequestBody
                    Card card
            ) {
        Game game = gameProvider.getGameByUuid(gameId);
        Player player = game.getPlayers().get(playerId);

        game.getTurn().purchaseCardForPlayer(card, player, game);

        return game;
    }

    @RequestMapping(value = "/play", method = RequestMethod.POST)
    Game play(
            @PathVariable("gameId")
            String gameId,
            @PathVariable("playerId")
            String playerId,
            @RequestBody
            Card card
    ) {
        Game game = gameProvider.getGameByUuid(gameId);
        Player player = game.getPlayers().get(playerId);

        Card playing = player.getHand()
                .stream()
                .filter(handCard -> handCard.getId().equals(card.getId()))
                .findFirst()
                .get();

        if(playing instanceof ActionCard) {
            game.getTurn().playCard((ActionCard)playing, player, game);
        } else {
            throw new IllegalStateException();
        }

        return game;
    }
}
