package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
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
    public RoomDto getRoomById(Integer id) {
        RoomEntity roomNotFound = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room with this ID not found"));
        return roomMapper.toDto(roomNotFound);
    }

    public List<RoomDto> getRoomsByHotelId(Integer hotelId) {
        List<RoomEntity> rooms = roomRepository.findByHotelId(hotelId);
        return roomMapper.toListDto(rooms);
    }

    public List<RoomDto> getAllRooms() {
        return roomMapper.toListDto(roomRepository.findAll());
    }

    public RoomDto addRoom(RoomDto room) {
        RoomEntity save = roomRepository.save(roomMapper.toEntity(room));
        return roomMapper.toDto(save);
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
