package com.github.nelson54.dominion.user.account;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends MongoRepository<AccountEntity, String> {

    Optional<AccountEntity> findByUserUsername(String username);

    //@Query("select count(a) from AccountEntity a where a.elo > :rank")
    //Long findRank(@Param("rank") Long rank);

    //@Query("select count(a) from AccountEntity a")
    //Long findTotalPlayers(@Param("rank") Long rank);
}
