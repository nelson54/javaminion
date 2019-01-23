package com.github.nelson54.dominion;

import com.github.nelson54.dominion.ai.AiStrategies;
import com.github.nelson54.dominion.ai.AiStrategy;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.services.CommandService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class GameFactory {
    private KingdomFactory kingdomFactory;
    private CommandService commandService;

    public GameFactory(KingdomFactory kingdomFactory, CommandService commandService) {
        this.kingdomFactory = kingdomFactory;
        this.commandService = commandService;
    }

    public Game createGame(Match match) {
        Game game = new Game(match.getId(), match.getSeed());
        try {
            game.setPlayers(new HashMap<>());
            game.setTurnOrder(new LinkedHashSet<>());

            Kingdom kingdom = kingdomFactory.getKingdomFromCards(new Random(match.getSeed()), match.getCards().getCardClasses(), match.getPlayerCount());
            game.setKingdom(kingdom);

            addPlayers(match.getParticipants(), game);
            game.nextPlayer();
            Iterator<Player> turnerator =  game.getTurnOrder().iterator();

            byte i = 0;

            while(turnerator.hasNext()) {
                turnerator.next().setOrder(i++);
            }

        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    private void addPlayers(Collection<MatchParticipant> players, Game game) {

        for(MatchParticipant player : players){
            Player p;

            if(player.isAi()){
                p = createAiPlayer(game, player.getAccount());
            } else {
                p = createHumanPlayer(game, player.getAccount());
            }

            game.getPlayers().put(p.getId(), p);
        }
    }

    private Player createHumanPlayer(Game game, Account account) {


        Player player = new Player(account);
        return getPlayer(game, player);
    }

    private Player getPlayer(Game game, Player player) {
        player.setGame(game);

        addStartingCardsToPlayer(player, game);

        game.getTurnOrder().add(player);

        player.resetForNextTurn(null);

        return player;
    }

    private Player createAiPlayer(Game game, Account account) {

        return createAiPlayer(game, account, AiStrategies.random());
    }

    private Player createAiPlayer(Game game, Account account, AiStrategy aiStrategy) {
        AiPlayer player = new AiPlayer(account);
        player.setCommandService(commandService);
        player.setAiStrategy(aiStrategy);
        return getPlayer(game, player);
    }

    private void addStartingCardsToPlayer(Player player, Game game) {
        Stream.of("Estate", "Estate", "Estate", "Copper", "Copper", "Copper", "Copper", "Copper", "Copper", "Copper")
                .forEach(name -> game.giveCardToPlayer(name, player));
    }
}
