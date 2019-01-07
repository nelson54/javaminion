package com.github.nelson54.dominion.persistence;

import com.github.nelson54.dominion.persistence.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {
}
