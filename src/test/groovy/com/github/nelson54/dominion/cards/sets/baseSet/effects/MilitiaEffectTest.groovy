package com.github.nelson54.dominion.cards.sets.baseSet.effects

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Player
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.Cards
import com.github.nelson54.dominion.cards.types.Card
import com.github.nelson54.dominion.cards.types.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

class MilitiaEffectTest extends DominionTestCase {

    ComplexActionCard militia
    Choice choice

    void setUp() {
        super.setUp()
        player = getCurrentPlayer()
        militia = (ComplexActionCard)game.giveCardToPlayer("Militia", player)
    }

    void testEffect() {
        Turn turn = game.getTurn()

        Set<Card> hand = new HashSet<>()

        for(Player p : game.getPlayers().values()){
            p.drawXCards(5)
        }

        player.setHand(hand)

        assertEquals "Phase is ACTION ", Phase.ACTION, turn.getPhase()

        turn.playCard(militia, player, game)

        //assertEquals "Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase()

        applyChoice(getChoice(), turn);


    }

    Choice getChoice(){
        return getNextPlayer().getChoices().stream()
                .filter( { ch -> (ch.getSource() == militia) } )
                .findFirst().get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = Cards.ofId(null, choice.getOptions().stream().findFirst().get());

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash)
        cr.setSource(player)

        choice.apply(cr, turn)
    }
}
