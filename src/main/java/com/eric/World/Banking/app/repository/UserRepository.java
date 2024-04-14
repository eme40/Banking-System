package com.eric.World.Banking.app.repository;

import com.eric.World.Banking.app.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
  Boolean existsByEmail(String email);
  Boolean existsByAccountNumber(String accountNumber);

  UserEntity findByAccountNumber(String AccountNUmber);
  Optional<UserEntity> findByEmail(String email);
}
