package com.example.mainfile.repository;

import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {

    List<HotelEntity> findByNameContainingAndAddressContaining(String name, String address);
    List<HotelEntity> findByStars(Integer stars);

    List<HotelEntity> findByNameContaining(String query);
}
