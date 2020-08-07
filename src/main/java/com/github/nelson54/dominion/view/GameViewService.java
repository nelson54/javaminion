package com.github.nelson54.dominion.view;

import com.github.nelson54.dominion.cards.types.Card;
import com.github.nelson54.dominion.game.Game;
import com.github.nelson54.dominion.game.Kingdom;
import com.github.nelson54.dominion.game.Player;
import com.github.nelson54.dominion.game.Turn;
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
                .gamePhase(game.getTurn().getPhase())
                .currentPlayerId(game.getTurn().getPlayerId())
                .cardMarket(cardMarket(game.getKingdom()))
                .playerDetails(playerDetails(player))
                .players(players(game))
                .turn(turn(game.getTurn(), player))
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
        boolean isCurrentPlayer = player.getId()
                .equals(player.getGame().getTurn().getPlayerId());

        List<CardViewModel> hand = player.getHand().stream()
                .map(this::fromCard).collect(Collectors.toList());

        List<CardViewModel> discard = player.getDiscard().stream()
                .map(this::fromCard).collect(Collectors.toList());

        return PlayerDetailsViewModel.builder()
                .deckSize(player.getDeck().size())
                .hand(hand)
                .discard(discard)
                .isMyTurn(isCurrentPlayer)
                .build();
    }

    private TurnViewModel turn(Turn turn, Player player) {
        boolean isCurrentPlayer = player.getId()
                .equals(turn.getPlayerId());

        return TurnViewModel.builder()
                .isMyTurn(isCurrentPlayer)
                .actions(turn.getActionPool())
                .buys(turn.getBuyPool())
                .money(turn.getMoneyPool() + turn.getMoney())
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
                        cardStackViewModel.setKingdomSortOrder(firstCard.getKingdomSortOrder());
                    }

                    return cardStackViewModel;
                }).collect(Collectors.toList());

    }


    private CardViewModel fromCard(Card card) {
        CardViewModel cardViewModel = CardViewModel.builder()
                .id(card.getId().toString())
                .name(card.getName())
                .cost(card.getCost())
                .types(card.getCardTypes())
                .build();

        if(card.getOwner() != null) {
            cardViewModel.setOwnerId(card.getOwner().getId());
        }

        return cardViewModel;
    }
}
