package com.example.mainfile.testService;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.repository.BookingRepository;
import com.example.mainfile.service.BookingService;
import com.example.mainfile.service.RoomService;
import com.example.mainfile.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookingService bookingService;


    @Test
    public void testSave() {
        BookingDto bookingDto = new BookingDto();
        BookingEntity bookingEntity = new BookingEntity();
        RoomDto roomDto = new RoomDto(); // Создайте объект RoomDto


        bookingDto.setRoom(roomDto);

        assertNotNull(bookingDto);
        assertNotNull(bookingEntity);

        when(bookingMapper.toEntity(bookingDto)).thenReturn(bookingEntity);
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);

        bookingService.save(bookingDto);

        verify(bookingRepository, times(1)).save(bookingEntity);
    }


    @Test
    public void testSaveBooking() {

        Integer roomId = 1;
        BookingDto bookingDto = new BookingDto();
        UUID userId = UUID.randomUUID();
        RoomDto roomDto = new RoomDto();
        UserDto userDto = new UserDto();
        roomDto.setRoomStatus(RoomStatus.AVAILABLE);

        when(roomService.getRoomById(roomId)).thenReturn(roomDto);
        when(userService.getById(userId)).thenReturn(Optional.of(userDto));

        bookingService.saveBooking(roomId, bookingDto, userId);

        verify(roomService, times(1)).getRoomById(roomId);
        verify(userService, times(1)).getById(userId);
        verify(roomService, times(1)).updateRoom(roomDto.getRoomId(), roomDto);
    }






    @Test
    public void testGetBookingsForUser() {
        UUID userId = UUID.randomUUID();
        List<BookingEntity> bookingEntities = new ArrayList<>();
        List<BookingDto> bookingDtos = new ArrayList<>();

        when(bookingRepository.findAllByUserId(userId)).thenReturn(bookingEntities);
        when(bookingMapper.toListDto(bookingEntities)).thenReturn(bookingDtos);

        List<BookingDto> result = bookingService.getBookingsForUser(userId);

        assertEquals(bookingDtos, result);
    }
}

