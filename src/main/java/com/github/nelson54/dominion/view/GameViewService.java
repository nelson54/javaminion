package com.github.nelson54.dominion.view;

import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Kingdom;
import com.github.nelson54.dominion.game.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameViewService {

    GameViewService() {}

    public GameViewModel toGameViewModel(Game game, Player player) {
        return GameViewModel.builder()
                .hash(game.hashCode())
                .currentPlayerId(game.getTurn().getPlayerId())
                .cardMarket(cardMarket(game.getKingdom()))
                .playerDetails(playerDetails(player))
                .players(players(game))
                .logs(new ArrayList<>(game.getLogs()))
                .build();
    }

    private List<PlayersViewModel> players(Game game) {
        return game.getTurnOrder().stream()
                .map((player)-> PlayersViewModel.builder()
                    .playerId(player.getId())

                    .cardsInDeck(player.getDeck().size())
                    .cardsInDiscard(player.getDiscard().size())
                    .cardsInHand(player.getHand().size())

                    .playerName(player.getName())

                    .turnOrder(player.getOrder())

                    .build()).collect(Collectors.toList());
    }

    private PlayerDetailsViewModel playerDetails(Player player) {

        List<CardViewModel> hand = player.getHand().stream()
                .map(this::fromCard).collect(Collectors.toList());

        List<CardViewModel> discard = player.getDiscard().stream()
                .map(this::fromCard).collect(Collectors.toList());

        return PlayerDetailsViewModel.builder()
                .deckSize(player.getDeck().size())
                .hand(hand)
                .discard(discard)
                .build();
    }

    private List<CardStackViewModel> cardMarket(Kingdom kingdom) {

        return kingdom.getCardMarket().keySet().stream()
                .map(key -> {
                    Card firstCard;
                    List<Card> cardList = kingdom.getCardMarket().get(key);

                    CardStackViewModel cardStackViewModel = CardStackViewModel.builder()
                            .size(cardList.size())
                            .name(key)
                            .build();

                    if(cardList.size() > 0) {
                        firstCard = cardList.get(0);
                        cardStackViewModel.setCardTypes(firstCard.getCardTypes());
                        cardStackViewModel.setCost(firstCard.getCost());
                        cardStackViewModel.setKingdom(firstCard.isKingdom());
                    }

                    return cardStackViewModel;
                }).collect(Collectors.toList());

    }


    private CardViewModel fromCard(Card card) {
        CardViewModel cardViewModel = CardViewModel.builder()
                .id(card.getId().toString())
                .name(card.getName())
                .cost(card.getCost())
                .build();

        if(card.getOwner() != null) {
            cardViewModel.setOwnerId(card.getOwner().getId());
        }

        return cardViewModel;
    }
}
