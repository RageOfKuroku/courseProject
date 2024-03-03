package com.example.mainfile.testService;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.UserMapper;
import com.example.mainfile.repository.UserRepository;
import com.example.mainfile.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSave() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(mapper.toEntity(userDto)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(userEntity);

        userService.save(userDto);

        verify(repository, times(1)).save(userEntity);
    }

    @Test
    public void testUpdate() {
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        UserEntity userEntity = new UserEntity();

        when(repository.findById(userDto.getId())).thenReturn(Optional.of(userEntity));
        when(repository.save(userEntity)).thenReturn(userEntity);

        userService.update(userDto);

        verify(repository, times(1)).save(userEntity);
    }

    @Test
    public void testFindAll() {
        List<UserEntity> userEntities = new ArrayList<>();

        when(repository.findAll()).thenReturn(userEntities);

        userService.findAll();

        verify(mapper, times(1)).toListDto(userEntities);
    }

    @Test
    public void testGetById() {
        UUID id = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        UserDto userDto = new UserDto();

        when(repository.findById(id)).thenReturn(Optional.of(userEntity));
        when(mapper.toDto(userEntity)).thenReturn(userDto);

        Optional<UserDto> returnedDto = userService.getById(id);

        assertEquals(userDto, returnedDto.get());
        verify(mapper, times(1)).toDto(userEntity);
    }



    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();

        doNothing().when(repository).deleteUserBookings(id);
        doNothing().when(repository).deleteById(id);

        userService.deleteById(id);

        verify(repository, times(1)).deleteUserBookings(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testIsExistsInDb() {
        UserDto dto = new UserDto();
        dto.setEmail("test@example.com");

        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new UserEntity()));

        boolean exists = userService.isExistsInDb(dto);

        assertTrue(exists);
    }

    @Test
    public void testFindByPasswordAndEmail() {
        UserDto dto = new UserDto();
        dto.setPassword("password");
        dto.setEmail("test@example.com");

        when(repository.findByPasswordAndEmail(dto.getPassword(), dto.getEmail())).thenReturn(Optional.of(new UserEntity()));

        Optional<UserEntity> userEntity = userService.findByPasswordAndEmail(dto);

        assertTrue(userEntity.isPresent());
    }

    @Test
    public void testGetByEmail() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        UserDto userDto = new UserDto();

        when(repository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(mapper.toDto(userEntity)).thenReturn(userDto);

        UserDto returnedDto = userService.getByEmail(email);

        assertEquals(userDto, returnedDto);
        verify(mapper, times(1)).toDto(userEntity);
    }

    @Test
    public void testLoadUserByUsername() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword("password");

        when(repository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
    }
}

