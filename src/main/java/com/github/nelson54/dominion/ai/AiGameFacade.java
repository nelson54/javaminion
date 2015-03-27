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

    AiGameFacade (Game game, Turn turn, Player player){
        this.game = game;
        this.player = player;
        this.turn = turn;
    }

    Phase getPhase(String gameId, String playerId){
        return turn.getPhase();
    }

    long getMoney(String gameId, String playerId){
        return turn.getMoneyPool();
    }

    long getActions(String gameId, String playerId){
        return turn.getActionPool();
    }

    long getBuys(String gameId, String playerId){
        return turn.getBuyPool();
    }

    Kingdom getKingdom(String gameId){
        return game.getKingdom();
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
}
