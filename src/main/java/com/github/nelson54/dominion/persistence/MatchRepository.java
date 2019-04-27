package com.github.nelson54.dominion.persistence;

import com.github.nelson54.dominion.match.MatchState;
import com.github.nelson54.dominion.persistence.entities.match.MatchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, Long> {

    @Query("select match from MatchEntity match where match.state in :states")
    Iterable<MatchEntity> findByStateIn(@Param("states") List<MatchState> states);

    @Query("select m " +
            "from MatchEntity m " +
            "left join m.players p " +
            "where p.id = :accountId ")
    Iterable<MatchEntity> findMatchEntitiesWithPlayer(@Param("accountId") Long accountId);
}
