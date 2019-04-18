package com.github.nelson54.dominion.persistence;

import com.github.nelson54.dominion.persistence.entities.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUserUsername(String username);
}
