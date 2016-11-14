package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.User;
import com.github.nelson54.dominion.cards.RecommendedCards;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MatchProvider {

    @Autowired
    GameFactory gameFactory;

    private Map<String, Match> matchesById;
    private Multimap<String, Match> gamesByPlayerId;
    //private Set<String> games;


    public MatchProvider() {
        matchesById = new HashMap<>();
        gamesByPlayerId = ArrayListMultimap.create();
    }

    public Match getMatchById(String id) {
        return matchesById.get(id);
    }

    public void addMatch(Match match)  {
        matchesById.put(match.getId(), match);

        match.getParticipants().forEach(p -> gamesByPlayerId.put(p.getUser().getId(), match));
    }

    public List<Match> getJoinableMatchesForUser(User user) {
        return matchesById
                .values()
                .stream()
                .filter((match) -> !match.hasUser(user) )
                .collect(Collectors.toList());
    }

    public List<Match> getAllMatches() {
        return matchesById
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    public void remove(Match match) {
        matchesById.remove(match.getId());
        match.getParticipants()
                .forEach(p ->
                        gamesByPlayerId
                                .get(p.getUser().getId())
                                .remove(match)
                );
    }
}