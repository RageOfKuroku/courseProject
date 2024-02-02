package com.example.mainfile.repository;

import com.example.mainfile.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {

}
