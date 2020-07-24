package com.github.nelson54.dominion.cards.sets.base.effects;

import com.github.nelson54.dominion.game.Phase;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.CardReference;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Derek on 3/16/2015.
 */
public class BureaucratEffectTest extends DominionTestCase {
    Card card;
    ComplexActionCard bureaucrat;

    @BeforeEach
    public void setup() {
        super.setup();
        player = getCurrentPlayer();
        bureaucrat = (ComplexActionCard)game.giveCardToPlayer("Bureaucrat", player);

    }

    @Test
    public void testEffect() {
        Turn turn = game.getTurn();
        Set<Card> hand = new HashSet<>();

        for(Player p : game.getPlayers().values()){
            p.drawXCards(5);
        }

        hand.add(bureaucrat);;
        assertEquals("Phase is ACTION ", Phase.ACTION, turn.getPhase());
        turn.playCard(bureaucrat, player, game);

        //assertEquals "Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase()

        applyChoice(getChoice(), turn);

        turn.setActionPool(turn.getActionPool() + 1);
        turn.getPlay().clear();
        hand.add(bureaucrat);

        turn.setPhase(Phase.ACTION);

        for (int i = 0; i < 5; i++) {
            Card card = game.giveCardToPlayer("Copper", player);
            hand.add(card);
        }

        turn.playCard(bureaucrat, player, game);
        turn.getPlay().clear();
        turn.endPhase();



    }

    Choice getChoice(){
        return game.getPlayers().get("2").getChoices().stream()
                .filter( ch -> (ch.getSource() == bureaucrat) )
                .findFirst()
                .get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = new CardReference(choice.getOptions().stream().findFirst().get());

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash);
        cr.setSource(player);

        choice.apply(cr, turn);
    }
}
