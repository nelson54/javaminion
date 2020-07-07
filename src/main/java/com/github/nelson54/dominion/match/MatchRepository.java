package com.github.nelson54.dominion.match;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchRepository extends MongoRepository<MatchEntity, String> {

    //@Query("select match from MatchEntity match where match.state in :states")
    //Iterable<MatchEntity> findByStateIn(@Param("states") List<MatchState> states);

    //@Query("select m " +
    //        "from MatchEntity m " +
    //        "left join m.players p " +
    //        "where p.id = :accountId ")
    //Iterable<MatchEntity> findMatchEntitiesWithPlayer(@Param("accountId") Long accountId);
}
