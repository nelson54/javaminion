package com.github.nelson54.dominion.view;

import java.util.List;
import java.util.Objects;

import lombok.*;

@Data @Builder @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class PlayerDetailsViewModel {
    int deckSize;
    List<CardViewModel> hand;
    List<CardViewModel> discard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDetailsViewModel that = (PlayerDetailsViewModel) o;
        return deckSize == that.deckSize &&
                hand.equals(that.hand) &&
                discard.equals(that.discard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deckSize, hand, discard);
    }
}
