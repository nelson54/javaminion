package com.github.nelson54.dominion.persistence;

import com.github.nelson54.dominion.persistence.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

}
