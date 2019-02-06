package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.choices.OptionType;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.commands.CommandRepository;
import com.github.nelson54.dominion.commands.CommandType;
import com.github.nelson54.dominion.exceptions.IncorrectPhaseException;
import com.github.nelson54.dominion.exceptions.InsufficientFundsException;
import com.github.nelson54.dominion.web.controllers.GameController;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommandService {

    private static final Logger logger = Logger.getLogger(CommandService.class);
    private CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public List<Command> findCommandsForGame(Game game) {
        return this.commandRepository.findByGameIdOrderByTimeAsc(game.getId())
                .orElse(new ArrayList<>());
    }

    public Command save(Command command) {
        return this.commandRepository.save(command);
    }

    public Game applyCommand(Game game, Command command) {
        String msg = null;
        Turn turn = game.getTurn();
        Card card = game.getAllCards().get(command.cardId);
        Player player = game.getPlayers().get(command.accountId);

        try {

            if (command.type.equals(CommandType.ACTION_COMMAND)) {
                turn.playCard((ActionCard) card, player, game);
            } else if (command.type.equals(CommandType.BUY_COMMAND)) {
                turn.purchaseCardForPlayer(card, player);
            } else if (command.type.equals(CommandType.CHOICE_RESPONSE)) {
                applyChoiceResponse(game, command);
            } else if (command.type.equals(CommandType.END_PHASE)) {
                turn.endPhase();
            } else if (command.type.equals(CommandType.END_TURN)) {

            }
        } catch (IncorrectPhaseException e) {
            msg = "Incorrect phase command for Player["+player.getId()+"] "+player.getName() +
                    " attempted command {"+ command.toString() +"} in Phase " + turn.getPhase();
        } catch (InsufficientFundsException e) {
            msg = "Insufficient funds for["+player.getId()+"] "+player.getName() +
                    " attempted to buy "+card.getName()+" with a money pool of" + turn.getMoney();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return game;
        }



        if(command.getId() == null && msg == null) {
            save(command);
        } else if (command.getId() == null && msg != null) {
            logger.info(msg);
            game.log(msg);
        }

        return game;
    }

    public void deleteAll() {
        commandRepository.deleteAll();
    }

    private void applyChoiceResponse(Game game, Command command) {
        ChoiceResponse choiceResponse = command.getChoiceResponse(game);

        Choice choice = game.getChoiceById(choiceResponse.getTargetChoice()).get();
        OptionType expectedType = choice.getExpectedAnswerType();

        Turn turn = game.getTurn();

        if (!choiceResponse.isDone()) {
            if (expectedType.equals(OptionType.CARD))
                choiceResponse.setCard(game.getAllCards().get(choiceResponse.getCard().getId()));
            else if (expectedType.equals(OptionType.LIST_OF_CARDS)) {
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