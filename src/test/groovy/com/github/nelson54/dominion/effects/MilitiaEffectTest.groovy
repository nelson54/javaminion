package com.github.nelson54.dominion.effects

import com.github.nelson54.dominion.DominionTestCase
import com.github.nelson54.dominion.Phase
import com.github.nelson54.dominion.Player
import com.github.nelson54.dominion.Turn
import com.github.nelson54.dominion.cards.Card
import com.github.nelson54.dominion.cards.Cards
import com.github.nelson54.dominion.cards.ComplexActionCard
import com.github.nelson54.dominion.cards.types.Card
import com.github.nelson54.dominion.cards.types.ComplexActionCard
import com.github.nelson54.dominion.choices.Choice
import com.github.nelson54.dominion.choices.ChoiceResponse

class MilitiaEffectTest extends DominionTestCase {
    Card card
    ComplexActionCard bureaucrat
    Choice choice

    void setUp() {
        super.setUp()
        Turn turn = game.getTurn()
        player = getCurrentPlayer()
        card = game.giveCardToPlayer("Militia", player)
        bureaucrat = (ComplexActionCard) card
    }

    void testEffect() {
        Turn turn = game.getTurn()

        Set<Card> hand = new HashSet<>()

        for (int i = 0; i < 5; i++) {
            Card card = game.giveCardToPlayer("Estate", player)
            hand.add(card)
        }

        for(Player p : game.getPlayers().values()){
            game.giveCardToPlayer("Estate", p);
        }

        player.setHand(hand)

        turn.playCard(bureaucrat, player, game)
        turn.getPlay().clear();
        assertEquals "Phase is WAITING_FOR_CHOICE ", Phase.WAITING_FOR_CHOICE, turn.getPhase()

        applyChoice(getChoice(), turn);
    }

    Choice getChoice(){
        return game.getChoices().stream()
                .filter( { ch -> ch.getSource().equals(card) } )
                .findFirst().get();
    }

    void applyChoice(Choice choice, Turn turn){
        Card toTrash = Cards.ofId(choice.getOptions().first());

        ChoiceResponse cr = new ChoiceResponse();
        cr.setCard(toTrash)
        cr.setSource(player)

        choice.apply(cr, turn)
    }
}
