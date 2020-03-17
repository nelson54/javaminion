package com.github.nelson54.dominion.game.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.game.choices.OptionType;
import com.github.nelson54.dominion.exceptions.IncorrectPhaseException;
import com.github.nelson54.dominion.exceptions.InsufficientFundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);
    private CommandRepository commandRepository;
    private ObjectMapper objectMapper;

    public CommandService(CommandRepository commandRepository, ObjectMapper objectMapper) {
        this.commandRepository = commandRepository;
        this.objectMapper = objectMapper;
    }

    public List<Command> findCommandsForGame(Game game) {
        return this.commandRepository.findByGameIdOrderByTimeAsc(game.getId())
                .orElse(new ArrayList<>());
    }



    public Command save(Command command) {
        return this.commandRepository.save(command);
    }

    public Game applyCommand(Game game, Command command) throws JsonProcessingException {
        String msg = null;
        game.setCommandTime(command.time);
        Turn turn = game.getTurn();
        Player player = game.getPlayers().get(command.accountId);
        Card card = game.getAllCards().get(command.cardId);

        try {

            if (command.type.equals(CommandType.ACTION_COMMAND)) {
                logMissingItem(game, command, player, card);
                turn.playCard((ActionCard) card, player, game);
            } else if (command.type.equals(CommandType.BUY_COMMAND)) {
                logMissingItem(game, command, player, card);
                turn.purchaseCardForPlayer(card, player);
            } else if (command.type.equals(CommandType.CHOICE_RESPONSE)) {
                applyChoiceResponse(game, command);
            } else if (command.type.equals(CommandType.END_PHASE)) {
                turn.endPhase();
            } else if (command.type.equals(CommandType.END_TURN)) {
                turn.endTurn();
            } else if (command.type.equals(CommandType.RESIGN)) {
                game.setResign(player);
            }
        } catch (IncorrectPhaseException e) {
            msg = "Incorrect phase command for Player[" + player.getId() + "] " + player.getName()
                    + " attempted command of type " + command.type + " in Phase " + turn.getPhase();
        } catch (InsufficientFundsException e) {
            msg = "Insufficient funds for[" + player.getId() + "] " + player.getName()
                    + " attempted to buy " + card.getName()
                    + " with a money pool of" + turn.getMoney();
        } finally {
            game.setCommandTime(null);
        }

        if(msg != null) {
            logger.error(msg);
            System.out.println(msg);
            return game;
        }

        if (command.getId() == null && msg == null && !game.getReadOnly()) {
            save(command);
        } else if (command.getId() == null && msg != null) {
            logger.error(msg);
            game.log(msg);
        }

        return game;
    }

    private void logMissingItem(Game game, Command command, Player player, Card card) {
        if(card == null) {
            game.log("Card: " + command.cardId + " not found.");
        }

        if(player == null) {
            game.log("Player: " + command.accountId + " not found.");
        }
    }


    private void deleteAll() {
        commandRepository.deleteAll();
    }

    private void applyChoiceResponse(Game game, Command command) throws JsonProcessingException {
        ChoiceResponse choiceResponse = command.getChoiceResponse(game);
        Choice choice = game.getChoiceById(choiceResponse.getTargetChoice()).get();
        OptionType expectedType = choice.getExpectedAnswerType();

        Turn turn = game.getTurn();

        if (!choiceResponse.isDone()) {
            if (expectedType.equals(OptionType.CARD)) {
                choiceResponse.setCard(game.getAllCards().get(choiceResponse.getCard().getId()));
            } else if (expectedType.equals(OptionType.LIST_OF_CARDS)) {
                Set<Card> choices = choiceResponse.getCards()
                        .stream()
                        .map(Card::getId)
                        .map(id -> game.getAllCards().get(id))
                        .collect(Collectors.toSet());

                choiceResponse.getCards().clear();
                choiceResponse.getCards().addAll(choices);
            }
        }

        choice.apply(choiceResponse, turn);
    }
}

