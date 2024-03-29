package com.github.nelson54.dominion.cards.sets.base.effects;


import com.github.nelson54.dominion.game.Phase;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
import com.github.nelson54.dominion.cards.Cards;
import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.cards.types.ComplexActionCard;
import com.github.nelson54.dominion.game.choices.Choice;
import com.github.nelson54.dominion.game.choices.ChoiceResponse;
import com.github.nelson54.dominion.DominionTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

public class MilitiaEffectTest extends DominionTestCase {

    ComplexActionCard militia;
    Choice choice;

    @BeforeEach
    public void setup() {
        super.setup();
        player = getCurrentPlayer();
        militia = (ComplexActionCard)game.giveCardToPlayer("Militia", player);
    }

    @Test
    public void testEffect() {
        Turn turn = game.getTurn();

        LinkedHashSet<Card> hand = new LinkedHashSet<>();

        for(Player p : game.getPlayers().values()){
            p.drawXCards(5);
        }

        player.setHand(hand);

        assertEquals("Phase is ACTION ", Phase.ACTION, turn.getPhase());

        turn.playCard(militia, player, game);

        //assertEquals "Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase()

        applyChoice(getChoice(), turn);


    }

    Choice getChoice(){
        return getNextPlayer().getChoices().stream()
                .filter( ch -> (ch.getSource() == militia) )
                .findFirst().get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = Cards.ofId(null, choice.getOptions().stream().findFirst().get());

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash);
        cr.setSource(player);

        choice.apply(cr, turn);
    }
}
