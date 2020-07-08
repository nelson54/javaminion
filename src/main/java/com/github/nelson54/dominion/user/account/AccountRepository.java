package com.github.nelson54.dominion.user.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends MongoRepository<AccountEntity, String> {

    Optional<AccountEntity> findByUserUsername(String username);

    @Query(value="{elo: { $gt : ?0 }}", count=true)
    Long findRank(Long elo);

}
