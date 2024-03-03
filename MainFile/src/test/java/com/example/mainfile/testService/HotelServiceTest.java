package com.example.mainfile.testService;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.repository.BookingRepository;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.repository.RoomRepository;
import com.example.mainfile.service.HotelService;
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
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    public void testGetHotelById() {
        HotelEntity hotelEntity = new HotelEntity();
        HotelDto hotelDto = new HotelDto();

        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotelEntity));
        when(hotelMapper.toDto(hotelEntity)).thenReturn(hotelDto);

        HotelDto result = hotelService.getHotelById(1);

        assertEquals(hotelDto, result);
    }

    @Test
    public void testGetAllHotels() {
        List<HotelEntity> hotels = new ArrayList<>();
        List<HotelDto> hotelDtos = new ArrayList<>();

        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.toListDto(hotels)).thenReturn(hotelDtos);

        List<HotelDto> result = hotelService.getAllHotels();

        assertEquals(hotelDtos, result);
    }

    @Test
    public void testCreateHotel() {
        HotelDto hotelDto = new HotelDto();
        HotelEntity hotelEntity = new HotelEntity();

        when(hotelMapper.toEntity(hotelDto)).thenReturn(hotelEntity);
        when(hotelRepository.save(hotelEntity)).thenReturn(hotelEntity);

        hotelService.createHotel(hotelDto);

        verify(hotelRepository, times(1)).save(hotelEntity);
    }

    @Test
    public void testUpdateHotel() {
        HotelDto hotelDto = new HotelDto();
        HotelEntity hotelEntity = new HotelEntity();

        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotelEntity));
        when(hotelMapper.toEntity(hotelDto)).thenReturn(hotelEntity);

        hotelService.updateHotel(1, hotelDto);

        verify(hotelRepository, times(1)).save(hotelEntity);
    }

    @Test
    public void testDeleteHotel() {
        HotelEntity hotelEntity = new HotelEntity();
        List<ReviewEntity> reviews = new ArrayList<>();
        List<RoomEntity> rooms = new ArrayList<>();
        List<BookingEntity> bookings = new ArrayList<>();

        when(hotelRepository.findById(anyInt())).thenReturn(Optional.of(hotelEntity));
        when(reviewRepository.findByHotel(hotelEntity)).thenReturn(reviews);
        when(roomRepository.findByHotel(hotelEntity)).thenReturn(rooms);
        when(bookingRepository.findByRoomIn(rooms)).thenReturn(bookings);

        hotelService.deleteHotel(1);

        verify(bookingRepository, times(1)).deleteAll(bookings);
        verify(reviewRepository, times(1)).deleteAll(reviews);
        verify(roomRepository, times(1)).deleteAll(rooms);
        verify(hotelRepository, times(1)).delete(hotelEntity);
    }


}

