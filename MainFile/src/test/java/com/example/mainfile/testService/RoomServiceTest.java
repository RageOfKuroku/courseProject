package com.example.mainfile.testService;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.mapper.RoomMapper;
import com.example.mainfile.repository.BookingRepository;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.RoomRepository;
import com.example.mainfile.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    public void testGetRoomById() {
        RoomEntity roomEntity = new RoomEntity();
        RoomDto roomDto = new RoomDto();

        when(roomRepository.findById(anyInt())).thenReturn(Optional.of(roomEntity));
        when(roomMapper.toDto(roomEntity)).thenReturn(roomDto);

        RoomDto result = roomService.getRoomById(1);

        assertEquals(roomDto, result);
    }

    @Test
    public void testGetRoomsByHotelId() {
        HotelEntity hotelEntity = new HotelEntity();
        List<RoomEntity> roomEntities = new ArrayList<>();
        List<RoomDto> roomDtos = new ArrayList<>();

        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotelEntity));
        when(roomRepository.findByHotel(hotelEntity)).thenReturn(roomEntities);
        when(roomMapper.toListDto(roomEntities)).thenReturn(roomDtos);

        List<RoomDto> result = roomService.getRoomsByHotelId(1);

        assertEquals(roomDtos, result);
    }

    @Test
    public void testGetAllRooms() {
        List<RoomEntity> roomEntities = new ArrayList<>();
        List<RoomDto> roomDtos = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(roomEntities);
        when(roomMapper.toListDto(roomEntities)).thenReturn(roomDtos);

        List<RoomDto> result = roomService.getAllRooms();

        assertEquals(roomDtos, result);
    }

    @Test
    public void testAddRoom() {
        RoomDto roomDto = new RoomDto();
        RoomEntity roomEntity = new RoomEntity();
        HotelEntity hotelEntity = new HotelEntity();
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(1);
        roomDto.setHotel(hotelDto);

        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotelEntity));
        when(roomMapper.toEntity(roomDto)).thenReturn(roomEntity);
        when(roomRepository.save(roomEntity)).thenReturn(roomEntity);

        roomService.addRoom(roomDto);

        verify(roomRepository, times(1)).save(roomEntity);
    }


    @Test
    public void testUpdateRoom() {
        RoomDto roomDto = new RoomDto();
        RoomEntity roomEntity = new RoomEntity();

        when(roomRepository.findById(anyInt())).thenReturn(Optional.of(roomEntity));
        when(roomMapper.toEntity(roomDto)).thenReturn(roomEntity);

        roomService.updateRoom(1, roomDto);

        verify(roomRepository, times(1)).save(roomEntity);
    }

    @Test
    public void testDeleteRoom() {
        RoomEntity roomEntity = new RoomEntity();
        List<BookingEntity> bookings = new ArrayList<>();

        when(roomRepository.findById(anyInt())).thenReturn(Optional.of(roomEntity));
        when(bookingRepository.findByRoom(roomEntity)).thenReturn(bookings);

        roomService.deleteRoom(1);

        verify(bookingRepository, times(1)).deleteAll(bookings);
        verify(roomRepository, times(1)).delete(roomEntity);
    }

    @Test
    public void testDeleteAll() {
        roomService.deleteAll();

        verify(roomRepository, times(1)).deleteAll();
    }
}

