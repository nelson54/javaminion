package com.github.nelson54.dominion.cards;

import com.github.nelson54.dominion.DominionTestCase;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.cards.types.ActionAttackCard;
import com.github.nelson54.dominion.cards.types.ActionCard;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Derek on 3/14/2015.
 */
public class WitchTest extends DominionTestCase {

    private static final Logger logger = Logger.getLogger(WitchTest.class);

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        player = game.getTurn().getPlayer();
        Player otherPlayer = game.nextPlayer();


        ActionAttackCard card = (ActionAttackCard)game.giveCardToPlayer("Witch", player);
        int startingHandSize = player.getHand().size();
        card.apply(player, game);
        assertEquals(2, player.getHand().size() - startingHandSize, "Playing Witch draws 2 cards");



        Collection<String> otherPlayerCardNames = otherPlayer.getAllCards()
                .values()
                .stream()
                .map((c)-> card.getName())
                .collect(Collectors.toList());

        game.getPlayers().values().forEach(this::showCards);

        long numberOfCurses = otherPlayerCardNames.stream()
                .filter((name) -> name.equals("Curse"))
                .count();

        assertEquals((long)1, numberOfCurses, "Playing Witch gives other players one Curse");
    }

    private void showCards(Player player) {
        logger.info("Showing cards for Player {" + player.getId() + "}");

        Collection<String> playerCardNames = player.getAllCards()
                .values()
                .stream()
                .map((c)-> c.getName())
                .collect(Collectors.toList());

        logger.info("Cards: " + String.join(", ", playerCardNames));
    }
}
