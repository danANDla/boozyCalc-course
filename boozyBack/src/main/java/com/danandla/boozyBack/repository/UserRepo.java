package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Long> {

}
