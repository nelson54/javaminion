package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.cards.GameCardSet;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchParticipant;
import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.services.AiPlayerService;
import com.github.nelson54.dominion.services.MatchService;
import com.github.nelson54.dominion.web.dto.MatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/dominion")
public class MatchController {


    private final AccountRepository accountRepository;

    private final AccountService accountService;
    private final AiPlayerService aiPlayerService;
    private MatchService matchService;

    public MatchController(
            AiPlayerService aiPlayerService,
            MatchService matchService,
            AccountService accountService,
            AccountRepository accountRepository) {

        this.aiPlayerService = aiPlayerService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.matchService = matchService;
    }

    @GetMapping(value = "/recommended")
    RecommendedCards[] getRecomendedCards(){
        return RecommendedCards.values();
    }

    @GetMapping(value = "/matches")
    Page<Match> matches(
            @RequestParam(defaultValue = "true") Boolean waitingForOpponent,
            @RequestParam(defaultValue = "false") Boolean isFinished,
            @RequestParam(defaultValue = "false") Boolean inProgress) {
        Account account = accountService.getAuthorizedAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        List<MatchState> matchStates = new ArrayList<>();

        if(waitingForOpponent) {
            matchStates.add(MatchState.WAITING_FOR_PLAYERS);
        }

        if(isFinished) {
            matchStates.add(MatchState.FINISHED);
        }

        if(inProgress) {
            matchStates.add(MatchState.IN_PROGRESS);
        }

        return new PageImpl<>(matchService.findByStateIn(matchStates));
    }

    @PostMapping(value = "/matches")
    @ResponseStatus(HttpStatus.CREATED)
    MatchDto createMatch(@RequestBody MatchDto matchDto) {

        Integer totalPlayers = matchDto.getCount();

        GameCardSet gameCardSet = GameCardSet.byName(matchDto.getCards());

        Match match = new Match(totalPlayers, gameCardSet);
        Account account = getAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        match.addParticipant(new MatchParticipant(account));

        Collection<MatchParticipant> participants = aiPlayerService
                 .random(matchDto.getNumberOfAiPlayers())
                 .stream()
                 .map(this::addUserToAccount)
                 .map(MatchParticipant::createAi)
                 .collect(Collectors.toList());

        match.addAiParticipants(participants);

        if (match.isReady()) {
            match.setMatchState(MatchState.IN_PROGRESS);
        } else {
            match.setMatchState(MatchState.WAITING_FOR_PLAYERS);
        }

        match = matchService.createMatch(match);

        matchDto.setId(match.getId());

        return matchDto;
    }

    @PatchMapping(value = "/matches/{gameId}")
    void join(@PathVariable Long gameId) {
        Match match = matchService.getMatch(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Account account = getAccount()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        matchService.addPlayerAccount(match, account);
    }

    private Account addUserToAccount(Account account) {
        return accountRepository.findByUserUsername(account.getFirstname()).get().asAccount();
    }


    private Optional<Account> getAccount() {
        return accountService.getAuthorizedAccount();
    }
}
