package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class AiGameFacade {

    private Game game;
    private Player player;
    private Turn turn;

    @Autowired
    GameProvider gameProvider;

    Phase getPhase(String gameId, String playerId){
        return getTurn(gameId, playerId).getPhase();
    }

    long getMoney(String gameId, String playerId){
        return getTurn(gameId, playerId).getMoneyPool();
    }

    long getActions(String gameId, String playerId){
        return getTurn(gameId, playerId).getActionPool();
    }

    long getBuys(String gameId, String playerId){
        return getTurn(gameId, playerId).getBuyPool();
    }

    Kingdom getKingdom(String gameId){
        return getGame(gameId).getKingdom();
    }

    Choice getChoice(){
        Iterator<Choice> choices = player.getChoices().iterator();
        return choices.next();
    }

    void play(ActionCard card){
        turn.playCard(card, player, game);
    }

    void buy(Card card){
        turn.purchaseCardForPlayer(card, player);
    }

    void respond(ChoiceResponse choiceResponse){
        game.getChoiceById(choiceResponse.getChoice())
                .ifPresent(choice -> choice.apply(choiceResponse, turn));
    }

    void endPhase(){
        turn.endPhase();
    }

    private Game getGame(String gameId){
        return gameProvider.getGameByUuid(gameId);
    }

    private Player getPlayer(String gameId, String playerId){
        return getGame(gameId).getPlayers().get(playerId);
    }

    private Turn getTurn(String gameId, String playerId){
        return getPlayer(gameId, playerId).getCurrentTurn();
    }

}
