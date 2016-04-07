package com.github.nelson54.dominion.effects
import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.Card
import com.github.nelson54.dominion.cards.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

class FeastEffectTest extends DominionTestCase {
    Card card
    ComplexActionCard feast
    Choice choice

    void setUp() {
        super.setUp()
        Turn turn = game.getTurn()
        card = game.giveCardToPlayer("Feast", player)
        feast = (ComplexActionCard) card

        turn.setPhase(Phase.ACTION)
        turn.playCard(feast, player, game)

        choice = getChoice();
    }

    void testEffect() {
        String toGain = choice.getOptions().first();

        assertEquals "Phase is WAITING_FOR_CHOICE ", turn.getPhase(), Phase.WAITING_FOR_CHOICE

        applyChoice(choice, turn);


        assertFalse "Player doesn't have trashed card", player.getAllCards().values().contains(card)
        assertTrue "Trashed card is in trash", game.getTrash().contains(card)

        assertTrue "Player has gained card", player.getAllCards().keySet().contains(toGain)

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
