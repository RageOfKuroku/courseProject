package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.repository.BookingRepository;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.RoomRepository;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.mapper.RoomMapper;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    public RoomDto getRoomById(Integer id) {
        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with id " + id + " not found"));
        RoomDto roomDto = roomMapper.toDto(roomEntity);

        HotelDto hotelDto = hotelMapper.toDto(roomEntity.getHotel());
        roomDto.setHotel(hotelDto);
        return roomDto;
    }

    public List<RoomDto> getRoomsByHotelId(Integer hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with this ID not found"));
        List<RoomEntity> rooms = roomRepository.findByHotel(hotel);
        return roomMapper.toListDto(rooms);
    }


    public List<RoomDto> getAllRooms() {
        return roomMapper.toListDto(roomRepository.findAll());
    }

    public void addRoom(RoomDto room) {
        Integer hotelId = room.getHotel().getId();
        HotelEntity hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with id " + hotelId + " not found"));
        RoomEntity roomEntity = roomMapper.toEntity(room);
        roomEntity.setHotel(hotel);
        RoomEntity savedRoom = roomRepository.save(roomEntity);
        roomMapper.toDto(savedRoom);
    }


    public RoomDto updateRoom(Integer id, RoomDto dto) {
        if(id != null){
            roomMapper.update(roomRepository.getReferenceById(id), dto);
            return dto;
        }else {
            throw new ResourceNotFoundException("Room with this ID not found");
        }
    }

    @Transactional
    public void deleteRoom(Integer roomId) {
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        List<BookingEntity> bookings = bookingRepository.findByRoom(room);

        bookingRepository.deleteAll(bookings);

        roomRepository.delete(room);
    }


    public void deleteAll(){
        roomRepository.deleteAll();
    }

    public List<RoomEntity> getRoomEntitiesByHotelId(Integer id) {
        return roomRepository.findByHotelId(id);
    }
}