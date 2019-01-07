package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.GameFactory;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MatchProvider {

    @Autowired
    GameFactory gameFactory;

    private Map<Long, Match> matchesById;
    private Multimap<Long, Match> gamesByAccount;

    public MatchProvider() {
        matchesById = new HashMap<>();
        gamesByAccount = ArrayListMultimap.create();
    }

    public Match getMatchById(Long id) {
        return matchesById.get(id);
    }

    public void addMatch(Match match)  {
        matchesById.put(match.getId(), match);

        match.getParticipants().forEach(p -> gamesByAccount.put(p.getAccount().getId(), match));
    }

    public List<Match> getJoinableMatchesForAccount(Account account) {
        return matchesById
                .values()
                .stream()
                .filter((match) -> !match.hasAccount(account) )
                .collect(Collectors.toList());
    }

    public List<Match> getAllMatches() {
        return new ArrayList<>(matchesById
                .values());
    }

    public void remove(Match match) {
        matchesById.remove(match.getId());
        match.getParticipants()
                .forEach(p ->
                        gamesByAccount
                                .get(p.getAccount().getId())
                                .remove(match)
                );
    }
}