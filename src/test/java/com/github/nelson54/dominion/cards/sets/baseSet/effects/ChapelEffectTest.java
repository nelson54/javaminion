package com.github.nelson54.dominion.cards.sets.baseSet.effects;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ChapelEffectTest extends DominionTestCase {
    Card card;
    ComplexActionCard chapel;
    Choice choice;
    Turn turn;

    @BeforeEach
    public void setup() {
        super.setup();
        turn = game.getTurn();
        card = game.giveCardToPlayer("Chapel", player);
        chapel = (ComplexActionCard) card;
    }

    @Test
    public void testEffect() {
        turn.setPhase(Phase.ACTION);
        turn.playCard(chapel, player, game);
        turn.getPlay().clear();
        choice = getChoice();
        Card toTrash = choice.getCardOptions().stream().findFirst().get();

        assertEquals("Phase is WAITING_FOR_CHOICE ", turn.getPhase(), Phase.WAITING_FOR_CHOICE);

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash);
        cr.setSource(player);

        choice.apply(cr, turn);

        choice = getChoice();

        assertFalse("Player doesn't have trashed card", player.getAllCards().values().contains(toTrash));
        assertTrue("Trashed card is in trash", game.getTrash().contains(toTrash));

        cr.setCard(null);
        cr.setDone(true);

        choice.apply(cr, turn);
    }

    Choice getChoice(){
        return player.getChoices().stream()
                .filter( ch -> ch.getSource().equals(card) )
                .findFirst().get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = choice.getCardOptions().stream().findFirst().get();

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash);
        cr.setSource(player);

        choice.apply(cr, turn);
    }
}
