package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.MatchService;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/dominion")
public class GameController {

    private static final Logger logger = Logger.getLogger(GameController.class);
    private AccountService accountService;
    private MatchService matchService;

    public GameController(AccountService accountService, MatchService matchService) {
        this.accountService = accountService;
        this.matchService = matchService;
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    Game getGame(@PathVariable("gameId") Long id) {
        return matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{gameId}/shuffle")
    Game shuffle(@PathVariable("gameId") Long gameId) {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        player.shuffle();

        return game;
    }

    @PostMapping("/{gameId}/draw")
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

    @PostMapping("/{gameId}/discard")
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

    @PostMapping(value = "/{gameId}/purchase")
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

        game = matchService.applyCommand(game, Command.buy(game, player, purchasedCard));

        return game;
    }

    @PostMapping(value = "/{gameId}/play")
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

        if (playing.getCardTypes().contains(CardType.ACTION)) {
            matchService.applyCommand(game, Command.action(game, player, playing));
        } else {
            throw new IllegalStateException();
        }

        return game;
    }

    @PostMapping(value = "/{gameId}/choice")
    Game chooseResponse(
            @PathVariable("gameId")
            Long gameId,
            @RequestBody
            ChoiceResponse choiceResponse
    ) {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        choiceResponse.setSource(player);

        if (choiceResponse.getCard() != null && choiceResponse.getCard().getId() != null) {
            choiceResponse.setCard(game.getAllCards().get(choiceResponse.getCard().getId()));
        }

        matchService.applyCommand(game, Command.choice(game, player, choiceResponse));

        return game;
    }

    @PostMapping(value = "/{gameId}/next-phase")
    Game endPhase(
            @PathVariable("gameId")
                    Long gameId
    ) {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        Choice choice = player.getChoices().peek();

        if(game.getTurn().getPhase().equals(Phase.WAITING_FOR_CHOICE) && choice != null) {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setChoice(choice.getId().toString());
            choiceResponse.setEffect(choice.getEffect());
            choiceResponse.setDone(true);
            choiceResponse.setSource(player);

            matchService.applyCommand(game, Command.choice(game, player, choiceResponse));
        } else {
            matchService.applyCommand(game, Command.endPhase(game, player));
        }
        return game;
    }

    Account getAccount() {
        return accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}