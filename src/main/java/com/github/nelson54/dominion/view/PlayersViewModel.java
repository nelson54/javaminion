package com.github.nelson54.dominion.view;

import com.github.nelson54.dominion.cards.CardRef;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data @Builder @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class PlayersViewModel {
    List<CardRef> revealedThisTurn;

    String playerId;
    String playerName;

    byte turnOrder;
    int cardsInHand;
    int cardsInDeck;
    int cardsInDiscard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayersViewModel that = (PlayersViewModel) o;
        return turnOrder == that.turnOrder &&
                cardsInHand == that.cardsInHand &&
                cardsInDeck == that.cardsInDeck &&
                cardsInDiscard == that.cardsInDiscard &&
                Objects.equals(revealedThisTurn, that.revealedThisTurn) &&
                playerId.equals(that.playerId) &&
                playerName.equals(that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(revealedThisTurn, playerId, playerName, turnOrder, cardsInHand, cardsInDeck, cardsInDiscard);
    }
}
