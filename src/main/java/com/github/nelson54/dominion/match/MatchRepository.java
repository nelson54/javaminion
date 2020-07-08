package com.github.nelson54.dominion.match;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchRepository extends MongoRepository<MatchEntity, String> {

    // @Query("select match from MatchEntity match where match.state in :states")
    @Query("{ state : { $in : ?0 } }")
    Iterable<MatchEntity> findByStateIn(List<MatchState> states);

    @Query("{ players : { $elemMatch : { $id : ?0 } } }")
    Iterable<MatchEntity> findByPlayerId(String accountId);
}
