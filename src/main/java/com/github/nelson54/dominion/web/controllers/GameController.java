package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.MatchService;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/com/github/nelson54/dominion")
public class GameController {

    private static final Logger logger = Logger.getLogger(GameController.class);
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
        Card purchasedCard = game.getAllCards().get(card.getId());

        matchService.applyCommand(game, Command.buy(game, player, purchasedCard));

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
        Card playing = game.getAllCards().get(card.getId());

        if (playing instanceof ActionCard) {
            matchService.applyCommand(game, Command.action(game, player, playing));
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
        Player player = game.getPlayers().get(account.getId());
        choiceResponse.setSource(player);

        choiceResponse.setCard(game.getAllCards().get(choiceResponse.getCard().getId()));

        matchService.applyCommand(game, Command.choice(game, player, choiceResponse));

        return game;
    }

    @RequestMapping(value = "/{gameId}/next-phase", method = RequestMethod.POST)
    Game endPhase(
            @PathVariable("gameId")
                    Long gameId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());

        matchService.applyCommand(game, Command.endPhase(game, player));

        return game;
    }

    Account getAccount() {
        return accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

}