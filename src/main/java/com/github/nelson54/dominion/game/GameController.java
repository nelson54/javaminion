package com.github.nelson54.dominion.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.user.account.AccountService;
import com.github.nelson54.dominion.match.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/dominion")
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final ObjectMapper objectMapper;
    private AccountService accountService;
    private MatchService matchService;

    public GameController(AccountService accountService, MatchService matchService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.matchService = matchService;
        this.objectMapper = objectMapper;
    }

    private Game getGame(Long id) {
        return matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS}, produces="application/json")
    @ResponseBody
    Game getGame(HttpServletResponse response, @PathVariable("gameId") Long id) throws JsonProcessingException {
        Game game = matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    @RequestMapping(value = "/{gameId}/command", method = {RequestMethod.GET, RequestMethod.OPTIONS}, produces="application/json")
    @ResponseBody
    Game getGameAtCommand(
            HttpServletResponse response,
            @PathVariable("commandId") String commandId,
            @PathVariable("gameId") Long id
    ) throws JsonProcessingException {
        Game game = matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    @PostMapping(value = "/{gameId}/purchase/{cardId}")
    Game purchase(
            HttpServletResponse response,
            @PathVariable("gameId") Long gameId,
            @PathVariable("cardId") Long cardId
    ) throws JsonProcessingException {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());

        Card purchasedCard = game.getKingdom().getAllCards().get(cardId);

        if(purchasedCard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        logger.info("Game[{}] - {}[{}] purchased Card[{}] ", gameId, account.getUser().getUsername(), account.getId(), purchasedCard.getName());

        game = matchService.applyCommand(game, Command.buy(game, player, purchasedCard));

        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    @PostMapping(value = "/{gameId}/play/{cardId}")
    Game play(
            HttpServletResponse response,
            @PathVariable("gameId") Long gameId,
            @PathVariable("cardId") Long cardId
    ) throws JsonProcessingException {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        Card playing = game.getAllCards().get(cardId);

        if (playing.getCardTypes().contains(CardType.ACTION)) {
            logger.info("Game[{}] - {}[{}] played Card[{}] ", gameId, account.getUser().getUsername(), account.getId(), playing.getName());
            matchService.applyCommand(game, Command.action(game, player, playing));
        } else {
            throw new IllegalStateException();
        }

        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    @PostMapping(value = "/{gameId}/resign")
    Game resign(
            HttpServletResponse response,
            @PathVariable("gameId") Long gameId
    ) throws JsonProcessingException {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());

        logger.info("Game[{}] - {}[{}] resigned.", gameId, account.getUser().getUsername(), account.getId());

        matchService.applyCommand(game, Command.resign(game, player));

        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    @PostMapping(value = "/{gameId}/choice")
    Game chooseResponse(
            HttpServletResponse response,
            @PathVariable("gameId")
            Long gameId,
            @RequestBody
            ChoiceResponse choiceResponse
    ) throws JsonProcessingException {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        choiceResponse.setSource(player);
        logger.info("Game[{}] - {}[{}] made a choice.", gameId, account.getUser().getUsername(), account.getId());

        if (choiceResponse.getCard() != null && choiceResponse.getCard().getId() != null) {
            choiceResponse.setCard(game.getAllCards().get(choiceResponse.getCard().getId()));
        }

        matchService.applyCommand(game, Command.choice(game, player, choiceResponse));

        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    @PostMapping(value = "/{gameId}/next-phase")
    Game endPhase(
            HttpServletResponse response,
            @PathVariable("gameId") Long gameId
    ) throws JsonProcessingException {
        Game game = matchService.getGame(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        Choice choice = player.getChoices().peek();

        logger.info("Game[{}] - {}[{}] is ending phase.", gameId, account.getUser().getUsername(), account.getId());

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

        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        return game;
    }

    Account getAccount() {
        return accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}