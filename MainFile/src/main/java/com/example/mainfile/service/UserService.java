package com.example.mainfile.service;

import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.UserMapper;
import com.example.mainfile.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Repository
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public void save(UserEntity user) {
        repository.save(user);
    }

    public List<UserDto> findAll() {
        return mapper.toListDto(repository.findAll());
    }

    public Optional<UserDto> getById(UUID id) {
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        UserDto userDto = mapper.toDto(userEntity);
        return Optional.of(userDto);
    }


    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public boolean isExistsInDb(UserDto dto) {
        return repository
                .findByPasswordAndEmail(dto.getPassword(), dto.getEmail())
                .isEmpty();
    }
    public Optional<UserEntity> findByPasswordAndEmail(UserDto dto){
        return repository.findByPasswordAndEmail(dto.getPassword(), dto.getEmail());
    }

}
