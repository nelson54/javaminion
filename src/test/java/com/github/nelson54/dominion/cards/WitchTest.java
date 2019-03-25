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

    private static final Logger logger = Logger.getLogger(CurseTest.class);

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testApply() {
        Player otherPlayer = getNextPlayer();

        ActionAttackCard card = (ActionAttackCard)game.giveCardToPlayer("Witch", player);
        int startingHandSize = player.getHand().size();
        card.apply(player, game);
        assertEquals(2, player.getHand().size() - startingHandSize, "Playing Witch draws 2 cards");

        Collection<String> cardNames = otherPlayer.getAllCards()
                .values()
                .stream()
                .map((c)-> card.getName())
                .collect(Collectors.toList());

        String allCardsByName = String.join(", ", cardNames);

        logger.info("Other player cards: " + allCardsByName);

        long numberOfCurses = cardNames.stream()
                .filter((name) -> name.equals("Curse"))
                .count();

        assertEquals((long)1, numberOfCurses, "Playing Witch gives other players one Curse");
    }
}
