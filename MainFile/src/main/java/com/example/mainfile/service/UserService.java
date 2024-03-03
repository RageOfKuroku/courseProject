package com.example.mainfile.service;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.UserMapper;
import com.example.mainfile.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        UserEntity entity = mapper.toEntity(userDto);
        repository.save(entity);
    }

    public void update(UserDto userDto) {
        if (userDto == null || userDto.getId() == null) {
            throw new IllegalArgumentException("UserDto or id cannot be null");
        }
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
        Optional<UserEntity> userEntity = repository.findById(id);
        if (userEntity.isPresent()) {
            UserDto userDto = mapper.toDto(userEntity.get());
            return Optional.of(userDto);
        } else {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }


    public void deleteById(UUID id) {
        repository.deleteUserBookings(id);
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
        if (userEntity != null) {
            return mapper.toDto(userEntity);
        } else {
            throw new ResourceNotFoundException("User with email " + email + " not found");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        try {
            return repository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Введён неверный логин или пароль"));
        } catch (UsernameNotFoundException e) {
            return new User("unknown", "", new ArrayList<>());
        }
    }


}





