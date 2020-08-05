package com.github.nelson54.dominion.view;

import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.Cost;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Data @Builder @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class CardViewModel {
    String id;
    String name;
    String ownerId;
    Cost cost;
    Set<CardType> types;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardViewModel that = (CardViewModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                Objects.equals(ownerId, that.ownerId) &&
                cost.equals(that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ownerId, cost);
    }
}
