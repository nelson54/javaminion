package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.*;
import com.github.nelson54.dominion.cards.ActionCard;
import com.github.nelson54.dominion.cards.Card;
import com.github.nelson54.dominion.cards.Cost;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

    Phase getPhase(){
        return turn.getPhase();
    }

    long getMoney(){
        return turn.getMoneyPool();
    }

    long getActions(){
        return turn.getActionPool();
    }

    long getBuys(){
        return turn.getBuyPool();
    }

    public Kingdom getKingdom(){
        return game.getKingdom();
    }

    Choice getChoice(){
        Iterator<Choice> choices = player.getChoices().iterator();
        return choices.next();
    }

    Set<Card> getAllCards(){
        return new HashSet(player.getAllCards().values());
    }

    public void play(ActionCard card){
        turn.playCard(card, player, game);
    }

    public void buy(Card card){
        turn.purchaseCardForPlayer(card, player);
    }

    void respond(ChoiceResponse choiceResponse){
        game.getChoiceById(choiceResponse.getChoice())
                .ifPresent(choice -> choice.apply(choiceResponse, turn));
    }

    public boolean canAffordCost(Cost cost){
        return turn.canAffordCost(cost);
    }

    public boolean canAffordCard(Card card){
        return canAffordCost(card.getCost());
    }

    boolean buyCardIf(String cardName, boolean... conditions){
        return true;
    }

    void endPhase(){
        turn.endPhase();
    }
}
