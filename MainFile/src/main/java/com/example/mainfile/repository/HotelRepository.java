package com.example.mainfile.repository;

import com.example.mainfile.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
}
