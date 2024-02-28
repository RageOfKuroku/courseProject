package com.example.mainfile.repository;

import com.example.mainfile.dto.OrderDto;
import com.example.mainfile.entity.OrderEntity;
import com.example.mainfile.entity.ProductEntity;
import com.example.mainfile.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findAllByUserId(UUID userId);

    OrderDto findByUserAndProduct(Optional<UserEntity> user, ProductEntity product);
}
