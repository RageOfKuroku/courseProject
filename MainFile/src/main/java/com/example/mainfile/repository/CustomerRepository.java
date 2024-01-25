package com.example.mainfile.repository;

import com.example.mainfile.entity.CustomerEntity;
import com.example.mainfile.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
