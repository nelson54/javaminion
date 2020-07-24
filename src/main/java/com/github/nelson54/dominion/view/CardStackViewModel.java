package com.github.nelson54.dominion.view;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.Cost;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Data @Builder @Setter @Getter @NoArgsConstructor @AllArgsConstructor @JsonAutoDetect
public class CardStackViewModel {
    String name;
    int size;
    boolean kingdom;
    Set<CardType> cardTypes;
    Cost cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardStackViewModel that = (CardStackViewModel) o;
        return size == that.size &&
                kingdom == that.kingdom &&
                name.equals(that.name) &&
                cardTypes.equals(that.cardTypes) &&
                cost.equals(that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, kingdom, cardTypes, cost);
    }
}
