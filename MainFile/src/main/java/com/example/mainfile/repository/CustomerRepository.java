package com.example.mainfile.repository;

import com.example.mainfile.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    Optional<CustomerEntity> findByUserId(UUID userId);
}
