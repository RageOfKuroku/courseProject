package com.example.mainfile.repository;

import com.example.mainfile.entity.StoreEntity;
import com.example.mainfile.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByStore(StoreEntity hotel);

    @Query("SELECT r.store FROM ProductEntity r WHERE r.productId = :productId")
    StoreEntity findStoreByProductId(@Param("productId") Integer productId);


}

