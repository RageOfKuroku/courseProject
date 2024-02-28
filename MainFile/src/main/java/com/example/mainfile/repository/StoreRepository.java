package com.example.mainfile.repository;

import com.example.mainfile.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    List<StoreEntity> findByNameContaining(String name);
}
