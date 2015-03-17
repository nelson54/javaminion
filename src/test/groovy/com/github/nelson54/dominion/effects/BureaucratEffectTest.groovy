package com.github.nelson54.dominion.effects

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.Card
import com.github.nelson54.dominion.cards.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

/**
 * Created by Derek on 3/16/2015.
 */
class BureaucratEffectTest extends DominionTestCase {
    Card card
    ComplexActionCard bureaucrat
    Choice choice

    void setUp() {
        super.setUp()
        Turn turn = game.getTurn()
        player = getCurrentPlayer()
        card = game.giveCardToPlayer("Bureaucrat", player)
        bureaucrat = (ComplexActionCard) card
    }

    void testEffect() {
        Turn turn = game.getTurn()

        Set<Card> hand = new HashSet<>()

        for (int i = 0; i < 5; i++) {
            Card card = game.giveCardToPlayer("Estate", player)
            hand.add(card)
        }

        player.setHand(hand)

        turn.playCard(bureaucrat, player, game)

        assertEquals "Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase()

        applyChoice(getChoice(), turn);

        assertEquals "Phase is ACTION ", Phase.ACTION, turn.getPhase()

        turn.actionPool++;

        hand.clear()

        for (int i = 0; i < 5; i++) {
            Card card = game.giveCardToPlayer("Copper", player)
            hand.add(card)
        }

        turn.playCard(bureaucrat, player, game)

        //assertEquals "Phase is ACTION ", Phase.ACTION, turn.getPhase()

    }

    Choice getChoice(){
        return game.getChoices().stream()
                .filter( { ch -> ch.getSource().equals(card) } )
                .findFirst().get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = choice.getCardOptions().first();

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash)
        cr.setSource(player)

        choice.apply(cr, turn)
    }
}
