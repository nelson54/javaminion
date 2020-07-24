package com.github.nelson54.dominion.view;
import java.util.List;
import java.util.Objects;

import lombok.*;

@Data @Builder @Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class GameViewModel {
    int hash;
    String currentPlayerId;
    List<PlayersViewModel> players;
    PlayerDetailsViewModel playerDetails;
    List<CardStackViewModel> cardMarket;
    List<String> logs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameViewModel that = (GameViewModel) o;
        return currentPlayerId.equals(that.currentPlayerId) &&
                players.equals(that.players) &&
                playerDetails.equals(that.playerDetails) &&
                cardMarket.equals(that.cardMarket) &&
                logs.equals(that.logs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPlayerId, players, playerDetails, cardMarket, logs);
    }
}
