package com.example.mainfile.repository;

import com.example.mainfile.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPasswordAndEmail(String password, String email);
}
