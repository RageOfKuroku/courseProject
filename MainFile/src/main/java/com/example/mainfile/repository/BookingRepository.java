package com.example.mainfile.repository;

import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    List<BookingEntity> findAllByUserId(UUID userId);

    List<BookingEntity> findByRoom(RoomEntity room);

    List<BookingEntity> findByRoomIn(List<RoomEntity> rooms);

}
