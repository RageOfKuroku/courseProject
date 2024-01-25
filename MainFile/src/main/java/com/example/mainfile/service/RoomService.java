package com.example.mainfile.service;

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

@Service
@RequiredArgsConstructor
@Getter
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    public RoomEntity getRoomById(Integer id) {
        return roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    public List<RoomDto> getAllRooms() {
        return roomMapper.toListDto(roomRepository.findAll());
    }

    public RoomEntity createRoom(RoomEntity room) {
        return roomRepository.save(room);
    }

    public void updateRoom(Integer id, RoomDto dto) {
        roomMapper.update(roomRepository.getReferenceById(id), dto);
    }

    public void deleteRoom(Integer id) {
        RoomEntity room = getRoomById(id);
        roomRepository.delete(room);
    }

    public void deleteAll(){
        roomRepository.deleteAll();
    }
}
