package com.github.nelson54.dominion.commands;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReactiveCommandRepository extends ReactiveMongoRepository<Command, String> {

    Flux<Command> findByGameId(Long gameId);
}
