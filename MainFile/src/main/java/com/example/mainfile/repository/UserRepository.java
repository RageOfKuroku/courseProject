package com.example.mainfile.repository;

import com.example.mainfile.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPasswordAndEmail(String password, String email);

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bookings WHERE user_id = :userId", nativeQuery = true)
    void deleteUserBookings(UUID userId);
}
