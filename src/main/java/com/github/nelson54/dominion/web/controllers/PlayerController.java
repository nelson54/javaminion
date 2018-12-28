package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.persistence.GameRepository;
import com.github.nelson54.dominion.persistence.entities.GameEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion/{gameId}/{playerId}")
public class PlayerController {

    private GameRepository gameRepository;
    private UsersProvider usersProvider;
    private GameProvider gameProvider;

    @RequestMapping("/shuffle")
    Game shuffle(
            @ModelAttribute
            Optional<User> user,
            @PathVariable("gameId")
            String gameId,
            @PathVariable("playerId")
            String playerId
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(gameId).asGame();
        Player player = game.getPlayers().get(playerId);

        player.shuffle();

        gameProvider.addGame(game);
        gameRepository.save(GameEntity.ofGame(game));

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
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(gameId).asGame();
        Player player = game.getPlayers().get(playerId);

        player.drawHand();

        gameProvider.addGame(game);
        gameRepository.save(GameEntity.ofGame(game));
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
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(gameId).asGame();
        Player player = game.getPlayers().get(playerId);

        player.discardHand();


        gameProvider.addGame(game);
        gameRepository.save(GameEntity.ofGame(game));
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
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(gameId).asGame();
        Player player = game.getPlayers().get(playerId);

        game.getTurn().purchaseCardForPlayer(card, player);


        gameProvider.addGame(game);
        gameRepository.save(GameEntity.ofGame(game));

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
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(gameId).asGame();
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

        gameProvider.addGame(game);
        gameRepository.save(GameEntity.ofGame(game));

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
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(gameId).asGame();
        Kingdom kingdom = game.getKingdom();
        Player player = game.getPlayers().get(playerId);
        Turn turn = player.getCurrentTurn();
        choiceResponse.setSource(player);

        Choice choice = game.getChoiceById(choiceResponse.getTargetChoice()).get();


        OptionType expectedType = choice.getExpectedAnswerType();

        if (!choiceResponse.isDone()) {
            if (expectedType.equals(OptionType.CARD))
                choiceResponse.setCard(kingdom.getAllCards().get(choiceResponse.getCard().getId()));
            else if (expectedType.equals(OptionType.LIST_OF_CARDS)) {
                Set<Card> choices = choiceResponse.getCards().stream()
                        .map(card -> card.getId())
                        .map(id -> kingdom.getAllCards().get(id))
                        .collect(Collectors.toSet());

                choiceResponse.getCards().clear();
                choiceResponse.getCards().addAll(choices);
            }
        }

        choice.apply(choiceResponse, turn);

        gameProvider.addGame(game);
        gameRepository.save(GameEntity.ofGame(game));

        return game;
    }

    @RequestMapping(value = "/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @ModelAttribute
            Optional<User> user,
            @PathVariable("gameId")
            String id
    ) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Game game = gameRepository.findOne(id).asGame();
        game.getTurn().endPhase();

        return game;
    }

    @ModelAttribute("authentication")
    Optional<User> user(){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //return Optional.of(usersProvider.getUserById(authentication.getName()));

        return Optional.empty();
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setUsersProvider(UsersProvider usersProvider) {
        this.usersProvider = usersProvider;
    }

    public void setGameProvider(GameProvider gameProvider) {
        this.gameProvider = gameProvider;
    }
}
