package com.github.nelson54.dominion.effects
import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.Card
import com.github.nelson54.dominion.cards.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

class ChapelEffectTest extends DominionTestCase {
    Card card
    ComplexActionCard chapel
    Choice choice
    Turn turn

    void setUp() {
        super.setUp()
        turn = game.getTurn()
        card = game.giveCardToPlayer("Chapel", player)
        chapel = (ComplexActionCard) card
    }

    void testEffect() {
        turn.setPhase(Phase.ACTION)
        turn.playCard(chapel, player, game)
        turn.getPlay().clear();
        choice = getChoice();
        Card toTrash = choice.getCardOptions().first();

        assertEquals "Phase is WAITING_FOR_CHOICE ", turn.getPhase(), Phase.WAITING_FOR_CHOICE

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash)
        cr.setSource(player)

        choice.apply(cr, turn)

        choice = getChoice()

        assertFalse "Player doesn't have trashed card", player.getAllCards().values().contains(toTrash)
        assertTrue "Trashed card is in trash", game.getTrash().contains(toTrash)

        cr.setCard(null)
        cr.setDone(true)

        choice.apply(cr, turn)
        assertEquals "Phase is ACTION ", Phase.ACTION, turn.getPhase()
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

        //return getChoice()
    }
}
