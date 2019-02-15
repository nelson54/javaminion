package com.github.nelson54.dominion.cards.sets.baseSet.effects

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Player
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.CardReference
import com.github.nelson54.dominion.cards.types.Card
import com.github.nelson54.dominion.cards.types.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

/**
 * Created by Derek on 3/16/2015.
 */
class BureaucratEffectTest extends DominionTestCase {
    Card card
    ComplexActionCard bureaucrat

    void setUp() {
        super.setUp()
        player = getCurrentPlayer()
        bureaucrat = (ComplexActionCard)game.giveCardToPlayer("Bureaucrat", player)

    }

    void testEffect() {
        Turn turn = game.getTurn()

        Set<Card> hand = new HashSet<>()

        for(Player p : game.getPlayers().values()){
            p.drawXCards(5)
        }

        hand.add(bureaucrat);
        assertEquals "Phase is ACTION ", Phase.ACTION, turn.getPhase()

        turn.playCard(bureaucrat, player, game)

        //assertEquals "Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase()

        applyChoice(getChoice(), turn)

        turn.actionPool++
        turn.getPlay().clear()
        hand.add(bureaucrat)

        turn.setPhase(Phase.ACTION)

        for (int i = 0; i < 5; i++) {
            Card card = game.giveCardToPlayer("Copper", player)
            hand.add(card)
        }

        turn.playCard(bureaucrat, player, game)
        turn.getPlay().clear()
        turn.endPhase()



    }

    Choice getChoice(){
        return getNextPlayer().getChoices().stream()
                .filter( { ch -> (ch.getSource() == bureaucrat) } )
                .findFirst()
                .get()
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = new CardReference(choice.getOptions().first())

        ChoiceResponse cr = new ChoiceResponse()
        cr.setCard(toTrash)
        cr.setSource(player)

        choice.apply(cr, turn)
    }
}
