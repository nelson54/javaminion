package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.cards.GameCards;
import com.github.nelson54.dominion.exceptions.InvalidCardSetName;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.match.MatchProvider;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.persistence.GameRepository;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;
import com.github.nelson54.dominion.persistence.entities.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping("/dominion")
public class MatchController {

    private GameRepository gameRepository;

    private final AccountRepository accountRepository;

    private final GameProvider gameProvider;

    private final MatchProvider matchProvider;

    private final GameFactory gameFactory;

    public MatchController(AccountRepository accountRepository, GameProvider gameProvider, GameFactory gameFactory, MatchProvider matchProvider) {
        this.accountRepository = accountRepository;
        this.gameProvider = gameProvider;
        this.gameFactory = gameFactory;
        this.matchProvider = matchProvider;
    }

    @RequestMapping(value="/matches", method= RequestMethod.GET)
    Page<Match> matches() {
        User user = getUser();
        return new PageImpl<>(matchProvider.getJoinableMatchesForUser(user));
    }

    @RequestMapping(value = "/matches", method = RequestMethod.POST)
    void createMatch(
            @RequestParam
            byte numberOfHumanPlayers,
            @RequestParam
            byte numberOfAiPlayers,
            @RequestParam
            String cards
    ) throws InstantiationException, IllegalAccessException {

        byte totalPlayers = (byte)(numberOfAiPlayers + numberOfHumanPlayers);

        GameCardSet gameCardSet;
        GameCards gameCards = GameCards.ofName(cards);
        if(gameCards != null) {
            gameCardSet = gameCards.getGameCardSet();
        } else {
            throw new InvalidCardSetName();
        }

        Match match = new Match(totalPlayers, gameCardSet);

        getAccount().ifPresent((account)->{
            match.addParticipant(new MatchParticipant(account));
            match.addAiParticipants(numberOfAiPlayers);
        });

        addMatch(match);
    }

    @RequestMapping(value="/matches", method=RequestMethod.PATCH)
    void join(
            @RequestParam
            Long matchId
    ) throws InstantiationException, IllegalAccessException {
        Match match = matchProvider.getMatchById(matchId);
        getAccount().ifPresent(account -> match.addParticipant(new MatchParticipant(account)));
        createGameIfReady(match);
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    private void addMatch (Match match) throws IllegalAccessException, InstantiationException {
        if(match.isReady()) {
            createGame(match);
        } else {
            matchProvider.addMatch(match);
        }
    }

    private void createGameIfReady(Match match) throws IllegalAccessException, InstantiationException {
        if(match.isReady()) {
            createGame(match);
        }
    }

    private void createGame(Match match) throws IllegalAccessException, InstantiationException {
        Game game = gameFactory.createGame(match);
        gameProvider.addGame(game);
        matchProvider.remove(match);

        GameEntity gameEntity = GameEntity.ofGame(game);
        gameRepository.save(gameEntity);

        GameEntity gameEntity1 = gameRepository.findById(game.getId()).get();

        System.out.println(gameEntity1.toString());

        try {
            gameEntity1.asGame();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Optional<Account> getAccount(){
        Optional<AccountEntity> accountEntityOptional = accountRepository.findByUserUsername(getUser().getUsername());
        return accountEntityOptional.map(AccountEntity::asAccount);
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }
}
