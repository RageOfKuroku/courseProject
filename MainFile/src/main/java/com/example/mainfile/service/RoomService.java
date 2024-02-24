package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.RoomRepository;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.mapper.RoomMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final BookingMapper bookingMapper;
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

    public RoomDto addRoom(RoomDto room) {
        Integer hotelId = room.getHotel().getHotelId();
        HotelEntity hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with id " + hotelId + " not found"));
        RoomEntity roomEntity = roomMapper.toEntity(room);
        roomEntity.setHotel(hotel);
        RoomEntity savedRoom = roomRepository.save(roomEntity);
        return roomMapper.toDto(savedRoom);
    }


    public RoomDto updateRoom(Integer id, RoomDto dto) {
        if(id != null){
            roomMapper.update(roomRepository.getReferenceById(id), dto);
            return dto;
        }else {
            throw new ResourceNotFoundException("Room with this ID not found");
        }
    }

    public void deleteRoom(Integer id) {
        if(id != null) {
            roomRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("Hotel with this ID not found");
        }
    }

    public void deleteAll(){
        roomRepository.deleteAll();
    }
}
