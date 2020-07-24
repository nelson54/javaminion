package com.github.nelson54.dominion.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.match.MatchService;
import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountService;
import com.github.nelson54.dominion.view.GameViewModel;
import com.github.nelson54.dominion.view.GameViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/v2/game")
public class GameViewController {

    private final Logger logger = LoggerFactory.getLogger(GameViewController.class);
    private final ObjectMapper objectMapper;
    private final GameViewService gameViewService;
    private AccountService accountService;
    private MatchService matchService;

    public GameViewController(AccountService accountService, MatchService matchService, ObjectMapper objectMapper, GameViewService gameViewService) {
        this.accountService = accountService;
        this.matchService = matchService;
        this.objectMapper = objectMapper;
        this.gameViewService = gameViewService;
    }

    @RequestMapping(value = "/{gameId}", method = {RequestMethod.GET, RequestMethod.OPTIONS}, produces="application/json")
    @ResponseBody
    public GameViewModel getGame(HttpServletResponse response, @PathVariable("gameId") String id) throws JsonProcessingException {
        Game game = matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String gameString = objectMapper.writeValueAsString(game);

        response.addHeader("hashcode", String.valueOf(gameString.hashCode()));

        Player player = game.getPlayers().get(getAccount().getId());
        return gameViewService.toGameViewModel(game, player);
    }

    @RequestMapping(value = "/{gameId}/command", method = {RequestMethod.GET, RequestMethod.OPTIONS}, produces="application/json")
    @ResponseBody
    public GameViewModel getGameAtCommand(
            HttpServletResponse response,
            @PathVariable("commandId") String commandId,
            @PathVariable("gameId") String id
    ) throws JsonProcessingException {
        Game game = matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        Player player = game.getPlayers().get(getAccount().getId());
        return gameViewService.toGameViewModel(game, player);
    }

    @PostMapping(value = "/{gameId}/purchase/{cardId}")
    public GameViewModel purchase(
            HttpServletResponse response,
            @PathVariable("gameId") String gameId,
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

        player = game.getPlayers().get(getAccount().getId());
        return gameViewService.toGameViewModel(game, player);
    }

    @PostMapping(value = "/{gameId}/play/{cardId}")
    public GameViewModel play(
            HttpServletResponse response,
            @PathVariable("gameId") String gameId,
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

        player = game.getPlayers().get(getAccount().getId());
        return gameViewService.toGameViewModel(game, player);
    }

    @PostMapping(value = "/{gameId}/surrender")
    public GameViewModel resign(
            HttpServletResponse response,
            @PathVariable("gameId") String gameId
    ) throws JsonProcessingException {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());

        logger.info("Game[{}] - {}[{}] resigned.", gameId, account.getUser().getUsername(), account.getId());

        game.setReadOnly(false);
        matchService.applyCommand(game, Command.resign(game, player));

        String gameJson = objectMapper.writeValueAsString(game);
        Integer hashCode = gameJson.hashCode();

        response.addHeader("hashcode", hashCode.toString());

        player = game.getPlayers().get(getAccount().getId());
        return gameViewService.toGameViewModel(game, player);
    }

    @PostMapping(value = "/{gameId}/choice")
    public GameViewModel chooseResponse(
            HttpServletResponse response,
            @PathVariable("gameId")
            String gameId,
            @RequestBody
            ChoiceResponse choiceResponse
    ) {
        Game game = getGame(gameId);
        Account account = getAccount();
        Player player = game.getPlayers().get(account.getId());
        choiceResponse.setSource(player);
        logger.info("Game[{}] - {}[{}] made a choice.", gameId, account.getUser().getUsername(), account.getId());

        if (choiceResponse.getCard() != null && choiceResponse.getCard().getId() != null) {
            choiceResponse.setCard(game.getAllCards().get(choiceResponse.getCard().getId()));
        }

        matchService.applyCommand(game, Command.choice(game, player, choiceResponse));

        player = game.getPlayers().get(getAccount().getId());
        GameViewModel gameViewModel = gameViewService.toGameViewModel(game, player);
        response.addHeader("hashcode", Integer.toString(gameViewModel.getHash()));
        return gameViewModel;
    }

    @PostMapping(value = "/{gameId}/next-phase")
    public GameViewModel endPhase(
            HttpServletResponse response,
            @PathVariable("gameId") String gameId
    ) {
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

        player = game.getPlayers().get(getAccount().getId());
        GameViewModel gameViewModel = gameViewService.toGameViewModel(game, player);
        response.addHeader("hashcode", Integer.toString(gameViewModel.getHash()));
        return gameViewModel;
    }

    Account getAccount() {
        return accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    private Game getGame(String id) {
        return matchService.getGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}