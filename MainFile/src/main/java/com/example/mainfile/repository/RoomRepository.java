package com.example.mainfile.repository;

import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    List<RoomEntity> findByHotel(HotelEntity hotel);

    List<RoomEntity> findByHotelId(Integer hotelId);
}

