package com.github.nelson54.dominion.persistence;

import com.github.nelson54.dominion.commands.Command;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommandRepository extends MongoRepository<Command, String> {

    public Optional<Command> findByGameId(Long gameId);

}
