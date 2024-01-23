package com.example.mainfile.service;

import com.example.mainfile.repository.RoomRepository;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.mapper.RoomMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class RoomService {
    private final RoomRepository repository;
    private final RoomMapper mapper;
    public void deleteById(Integer id){
        repository.deleteById(id);
    }
    public RoomEntity save(RoomEntity product) {
        return repository.save(product);
    }
    public void update(Integer id, RoomDto dto){
        mapper.update(repository.getReferenceById(id), dto);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
}
