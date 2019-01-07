package com.github.nelson54.dominion.persistence;

import com.github.nelson54.dominion.persistence.entities.match.MatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, Long> {
}
