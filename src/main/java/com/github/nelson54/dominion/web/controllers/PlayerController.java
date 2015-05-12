package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion/{gameId}/{playerId}")
public class PlayerController {

    @Autowired
    UsersProvider usersProvider;

    @Autowired
    GameProvider gameProvider;

    @RequestMapping("/shuffle")
    Game shuffle(
            @ModelAttribute
            Optional<User> user,
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
            @ModelAttribute
            Optional<User> user,
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
            @ModelAttribute
            Optional<User> user,
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
            @ModelAttribute
            Optional<User> user,
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
            @ModelAttribute
            Optional<User> user,
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
            @ModelAttribute
            Optional<User> user,
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

    @RequestMapping(value = "/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @ModelAttribute
            Optional<User> user,
            @PathVariable("gameId")
            String id
    ) {
        Game game = gameProvider.getGameByUuid(id);
        game.getTurn().endPhase();

        return game;
    }

    @ModelAttribute("authentication")
    Optional<User> user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.of(usersProvider.getUserById(authentication.getName()));
    }
}
