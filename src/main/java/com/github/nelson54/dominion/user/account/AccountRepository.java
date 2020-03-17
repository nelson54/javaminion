package com.github.nelson54.dominion.user.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUserUsername(String username);

    @Query("select count(a) from AccountEntity a where a.elo > :rank")
    Long findRank(@Param("rank") Long rank);

    @Query("select count(a) from AccountEntity a")
    Long findTotalPlayers(@Param("rank") Long rank);
}
