package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.persistence.GameRepository;
import com.github.nelson54.dominion.persistence.entities.GameEntity;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion/{gameId}/{playerId}")
public class PlayerController {

    private MatchService matchService;
    private AccountService accountService;

    @RequestMapping("/shuffle")
    Game shuffle(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId,
            @PathVariable("playerId")
            String playerId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        Player player = game.getPlayers().get(playerId);

        player.shuffle();

        //gameProvider.addGame(game);
        //gameRepository.save(GameEntity.ofGame(game));

        return game;
    }

    @RequestMapping("/draw")
    Game drawHand(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId,
            @PathVariable("playerId")
            String playerId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Player player = game.getPlayers().get(playerId);

        player.drawHand();

        //gameProvider.addGame(game);
        //gameRepository.save(GameEntity.ofGame(game));
        return game;
    }

    @RequestMapping("/discard")
    Game discardHand(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId,
            @PathVariable("playerId")
            String playerId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Player player = game.getPlayers().get(playerId);

        player.discardHand();


        //gameProvider.addGame(game);
        //gameRepository.save(GameEntity.ofGame(game));
        return game;
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    Game purchase(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId,
            @PathVariable("playerId")
            String playerId,
            @RequestBody
            Card card
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Player player = game.getPlayers().get(playerId);

        game.getTurn().purchaseCardForPlayer(card, player);


        //gameProvider.addGame(game);
        //gameRepository.save(GameEntity.ofGame(game));

        return game;
    }

    @RequestMapping(value = "/play", method = RequestMethod.POST)
    Game play(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId,
            @PathVariable("playerId")
            String playerId,
            @RequestBody
            Card card
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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

        //gameProvider.addGame(game);
        //gameRepository.save(GameEntity.ofGame(game));

        return game;
    }

    @RequestMapping(value = "/choice", method = RequestMethod.POST)
    Game chooseCard(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId,
            @PathVariable("playerId")
            String playerId,
            @RequestBody
            ChoiceResponse choiceResponse
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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

        //gameProvider.addGame(game);
        //gameRepository.save(GameEntity.ofGame(game));

        return game;
    }

    @RequestMapping(value = "/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @ModelAttribute
            Optional<Account> account,
            @PathVariable("gameId")
            Long gameId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        game.getTurn().endPhase();

        return game;
    }

    @ModelAttribute("authentication")
    Optional<Account> getAccount(){
        return accountService.getAuthorizedAccount();
    }

    @Inject
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
