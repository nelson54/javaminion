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
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.persistence.GameRepository;
import com.github.nelson54.dominion.persistence.entities.GameEntity;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.MatchService;
import com.github.nelson54.dominion.web.dto.MatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController()
@RequestMapping("/dominion")
public class MatchController {

    private GameRepository gameRepository;

    private final AccountRepository accountRepository;

    private final GameProvider gameProvider;

    private final MatchProvider matchProvider;

    private final GameFactory gameFactory;

    private final AccountService accountService;
    private MatchService matchService;

    public MatchController(MatchService matchService, AccountService accountService, AccountRepository accountRepository, GameProvider gameProvider, GameFactory gameFactory, MatchProvider matchProvider) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.gameProvider = gameProvider;
        this.gameFactory = gameFactory;
        this.matchProvider = matchProvider;
        this.matchService = matchService;
    }

    @GetMapping(value="/matches")
    Page<Match> matches() {
        Account account = accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return new PageImpl<>(matchProvider.getJoinableMatchesForAccount(account));
    }

    @PostMapping(value = "/matches")
    void createMatch(@RequestBody MatchDto matchDto) {

        Integer totalPlayers = (matchDto.getNumberOfAiPlayers() + matchDto.getNumberOfHumanPlayers());

        GameCardSet gameCardSet;
        GameCards gameCards = GameCards.ofName(matchDto.getCards());
        if(gameCards != null) {
            gameCardSet = gameCards.getGameCardSet();
        } else {
            throw new InvalidCardSetName();
        }

        Match match = new Match(totalPlayers, gameCardSet);
        Account account = getAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        match.addParticipant(new MatchParticipant(account));
        match.addAiParticipants(matchDto.getNumberOfAiPlayers());


        if(match.isReady()) {
            match.setMatchState(MatchState.IN_PROGRESS);
        } else {
            match.setMatchState(MatchState.WAITING_FOR_PLAYERS);
        }

        matchService.createMatch(match);
    }

    @PatchMapping(value="/matches")
    void join(
            @RequestParam
            Long matchId
    ) throws InstantiationException, IllegalAccessException {
        Match match = matchProvider.getMatchById(matchId);

        Account account = getAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        match.addParticipant(new MatchParticipant(account));

        createGameIfReady(match);
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

    @Inject
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    private Optional<Account> getAccount(){
        return accountService.getAuthorizedAccount();
    }
}
