package com.example.mainfile.service;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.UserMapper;
import com.example.mainfile.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BookingMapper bookingMapper;
    private final PasswordEncoder passwordEncoder;

    public void save(UserDto userDto) {
        UserEntity entity = mapper.toEntity(userDto);
        repository.save(entity);
    }

    public void update(UserDto userDto) {
        UserEntity userEntity = repository.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userDto.getId() + " not found"));

        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());

        repository.save(userEntity);
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
                .findByEmail(dto.getEmail())
                .isPresent();
    }

    public Optional<UserEntity> findByPasswordAndEmail(UserDto dto) {
        return repository.findByPasswordAndEmail(dto.getPassword(), dto.getEmail());
    }

    public UserDto getByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return mapper.toDto(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(RuntimeException::new);
    }



}
