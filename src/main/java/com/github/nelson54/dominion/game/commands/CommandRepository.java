package com.github.nelson54.dominion.game.commands;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommandRepository extends MongoRepository<Command, String> {

    Optional<List<Command>> findByGameIdOrderByTimeAsc(Long gameId);


    Optional<List<Command>> findByAccountIdAndBuyNameNotNull(Long accountId);
}
