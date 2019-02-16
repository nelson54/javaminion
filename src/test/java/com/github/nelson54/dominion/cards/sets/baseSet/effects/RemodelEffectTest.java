package com.github.nelson54.dominion.cards.sets.baseSet.effects;


import com.github.nelson54.dominion.Kingdom;
import com.github.nelson54.dominion.Phase;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class RemodelEffectTest extends DominionTestCase {
    Card card;
    ComplexActionCard remodel;
    Choice choice;

    @BeforeEach
    public void setup() {
        super.setup();
        Turn turn = game.getTurn();
        card = game.giveCardToPlayer("Remodel", player);
        remodel = (ComplexActionCard) card;
        turn.setPhase(Phase.ACTION);
        turn.playCard(remodel, player, game);
        choice = getChoice();
    }

    @Test
    public void testEffect() {
        Turn turn = game.getTurn();
        Kingdom kingdom = game.getKingdom();
        Card toTrash = kingdom.getAllCards().get(choice.getOptions().stream().findFirst().get());

        assertEquals("Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase());

        Choice choice = getChoice();

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash);
        cr.setSource(player);

        choice.apply(cr, turn);

        Choice nextChoice = getChoice();

        ChoiceResponse ncr = new ChoiceResponse();
        Card toGain = kingdom.getAllCards().get(nextChoice.getOptions().stream().findFirst().get());

        ncr.setCard(toGain);
        ncr.setSource(player);

        nextChoice.apply(ncr, turn);



        assertFalse("Player doesn't have trashed card", player.getAllCards().values().contains(toTrash));
        assertTrue("Trashed card is in trash", game.getTrash().contains(toTrash));

        assertTrue("Player has gained card", player.getAllCards().values().contains(toGain));
        assertEquals("Phase is ACTION ", Phase.ACTION, turn.getPhase());
    }

    Choice getChoice(){
        return player.getChoices().stream().findFirst().get();
    }


}
