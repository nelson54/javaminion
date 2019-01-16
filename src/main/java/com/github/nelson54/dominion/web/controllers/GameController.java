package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dominion")
public class GameController {


    private AccountService accountService;
    private MatchService matchService;

    public GameController(AccountService accountService, MatchService matchService) {
        this.accountService = accountService;
        this.matchService = matchService;
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    RecommendedCards[] getRecomendedCards(){
        return RecommendedCards.values();
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(@PathVariable("gameId") Long id) {
        return matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping("/{gameId}/shuffle")
    Game shuffle(@PathVariable("gameId") Long gameId) {
        Game game = getGame(gameId);

        Account account = getAccount();

        Player player = game.getPlayers().get(account.getId());

        player.shuffle();

        return game;
    }

    @RequestMapping("/{gameId}/draw")
    Game drawHand(
            @PathVariable("gameId")
                    Long gameId
    ) {
        Game game = getGame(gameId);

        Account account = getAccount();

        Player player = game.getPlayers().get(account.getId());

        player.drawHand();

        return game;
    }

    @RequestMapping("/{gameId}/discard")
    Game discardHand(
            @PathVariable("gameId")
                    Long gameId
    ) {
        Game game = getGame(gameId);

        Account account = getAccount();

        Player player = game.getPlayers().get(account.getId());

        player.discardHand();

        return game;
    }

    @RequestMapping(value = "/{gameId}/purchase", method = RequestMethod.POST)
    Game purchase(
            @PathVariable("gameId")
                    Long gameId,
            @RequestBody
                    Card card
    ) {
        Game game = getGame(gameId);

        Account account = getAccount();

        Player player = game.getPlayers().get(account.getId());

        game.getTurn().purchaseCardForPlayer(card, player);

        return game;
    }

    @RequestMapping(value = "/{gameId}/play", method = RequestMethod.POST)
    Game play(
            @PathVariable("gameId")
                    Long gameId,
            @RequestBody
                    Card card
    ) {
        Game game = getGame(gameId);

        Account account = getAccount();

        Player player = game.getPlayers().get(account.getId());

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

    @RequestMapping(value = "/{gameId}/choice", method = RequestMethod.POST)
    Game chooseCard(
            @PathVariable("gameId")
                    Long gameId,
            @RequestBody
                    ChoiceResponse choiceResponse
    ) {
        Game game = getGame(gameId);

        Account account = getAccount();

        Kingdom kingdom = game.getKingdom();
        Player player = game.getPlayers().get(account.getId());
        Turn turn = player.getCurrentTurn();
        choiceResponse.setSource(player);

        Choice choice = game.getChoiceById(choiceResponse.getTargetChoice()).get();


        OptionType expectedType = choice.getExpectedAnswerType();

        if (!choiceResponse.isDone()) {
            if (expectedType.equals(OptionType.CARD))
                choiceResponse.setCard(kingdom.getAllCards().get(choiceResponse.getCard().getId()));
            else if (expectedType.equals(OptionType.LIST_OF_CARDS)) {
                Set<Card> choices = choiceResponse.getCards()
                        .stream()
                        .map(Card::getId)
                        .map(id -> kingdom.getAllCards().get(id))
                        .collect(Collectors.toSet());

                choiceResponse.getCards().clear();
                choiceResponse.getCards().addAll(choices);
            }
        }

        choice.apply(choiceResponse, turn);

        return game;
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @PathVariable("gameId")
                    Long gameId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        game.getTurn().endPhase();

        return game;
    }

    Account getAccount() {
        return accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

}