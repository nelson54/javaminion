package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.GameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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

        game.getTurn().purchaseCardForPlayer(card, player);

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

        if (playing instanceof ActionCard) {
            game.getTurn().playCard((ActionCard) playing, player, game);
        } else {
            throw new IllegalStateException();
        }

        return game;
    }

    @RequestMapping(value = "/choice", method = RequestMethod.POST)
    Game chooseCard(
            @PathVariable("gameId")
            String gameId,
            @PathVariable("playerId")
            String playerId,
            @RequestBody
            ChoiceResponse choiceResponse
    ) {
        Game game = gameProvider.getGameByUuid(gameId);
        Kingdom kingdom = game.getKingdom();
        Player player = game.getPlayers().get(playerId);
        Turn turn = player.getCurrentTurn();
        choiceResponse.setSource(player);

        Choice choice = game.getChoiceById(choiceResponse.getTargetChoice()).get();


        OptionType expectedType = choice.getExpectedAnswerType();

        if (!choiceResponse.isDone()) {
            if (expectedType.equals(OptionType.CARD)) {
                choiceResponse.setCard(kingdom.getAllCards().get(choiceResponse.getCard().getId().toString()));
            } else if (expectedType.equals(OptionType.LIST_OF_CARDS)) {
                Set<Card> choices = choiceResponse.getCards().stream()
                        .map(card -> card.getId().toString())
                        .map(id -> kingdom.getAllCards().get(id))
                        .collect(Collectors.toSet());

                choiceResponse.getCards().clear();
                choiceResponse.getCards().addAll(choices);
            }
        }

        choice.apply(choiceResponse, turn);

        return game;
    }
}
