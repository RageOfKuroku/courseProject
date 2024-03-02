package com.example.mainfile.repository;

import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
        List<ReviewEntity> findByHotel(HotelEntity hotel);

        ReviewEntity findByUserAndHotel(UserEntity user, HotelEntity hotel);
    }
