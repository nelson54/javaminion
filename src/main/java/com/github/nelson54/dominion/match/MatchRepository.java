package com.github.nelson54.dominion.match;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchRepository extends PagingAndSortingRepository<MatchEntity, String> {

    // @Query("select match from MatchEntity match where match.state in :states")
    @Query("{ state : { $in : ?0 } }")
    Page<MatchEntity> findByStateIn(List<MatchState> states, Pageable page);

    @Query("{ players : { $elemMatch : { $id : ?0 } } }")
    List<MatchEntity> findByPlayerId(String accountId);
}
