package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    Optional<Object> findByUsername(String username);
}
