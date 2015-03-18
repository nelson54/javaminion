package com.github.nelson54.dominion.effects
import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.Card
import com.github.nelson54.dominion.cards.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

class RemodelEffectTest extends DominionTestCase {
    Card card
    ComplexActionCard remodel
    Choice choice

    void setUp() {
        super.setUp()
        Turn turn = game.getTurn()
        card = game.giveCardToPlayer("Remodel", player)
        remodel = (ComplexActionCard) card

        turn.playCard(remodel, player, game)
        //bureaucrat.apply(player, game)

        choice = getChoice();
    }

    void testEffect() {
        Turn turn = game.getTurn()

        Card toTrash = choice.getCardOptions().first();

        assertEquals "Phase is WAITING_FOR_CHOICE ", turn.getPhase(), Phase.WAITING_FOR_CHOICE

        applyChoice(choice, turn);

        Choice nextChoice = getChoice()

        ChoiceResponse ncr = new ChoiceResponse();
        Card toGain = nextChoice.getCardOptions().first()

        ncr.setCard(toGain)
        ncr.setSource(player)

        nextChoice.apply(ncr, turn)

        assertEquals "Phase is ACTION ", turn.getPhase(), Phase.ACTION

        assertFalse "Player doesn't have trashed card", player.getAllCards().values().contains(toTrash)
        assertTrue "Trashed card is in trash", game.getTrash().contains(toTrash)

        assertTrue "Player has gained card", player.getAllCards().values().contains(toGain)

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