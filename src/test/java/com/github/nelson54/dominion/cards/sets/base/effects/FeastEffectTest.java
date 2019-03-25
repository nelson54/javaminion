package com.github.nelson54.dominion.cards.sets.base.effects;


import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.CardReference;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class FeastEffectTest extends DominionTestCase {
    Card card;
    ComplexActionCard feast;
    Choice choice;

    @BeforeEach
    public void setup() {
        super.setup();
        Turn turn = game.getTurn();
        card = game.giveCardToPlayer("Feast", player);
        feast = (ComplexActionCard) card;

        turn.setPhase(Phase.ACTION);
        turn.playCard(feast, player, game);

        choice = getChoice();
    }

    @Test
    public void testEffect() {
        Long toGain = choice.getOptions().stream().findFirst().get();
        assertEquals("Phase is WAITING_FOR_CHOICE ", turn.getPhase(), Phase.WAITING_FOR_CHOICE);
        applyChoice(choice, turn);
        assertFalse("Player doesn't have trashed card", player.getAllCards().values().contains(card));
        assertTrue("Trashed card is in trash", game.getTrash().contains(card));
        assertTrue("Player has gained card", player.getAllCards().keySet().contains(toGain));
    }

    Choice getChoice(){
        return player.getChoices().stream()
                .filter( ch -> ch.getSource().equals(card) )
                .findFirst().get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = new CardReference(choice.getOptions().stream().findFirst().get());

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash);
        cr.setSource(player);

        choice.apply(cr, turn);
    }
}
