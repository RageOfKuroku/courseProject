package com.example.mainfile.service;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomService roomService;
    private final UserService userService;

    public BookingDto save(BookingDto booking) {
        if (booking.getRoom() == null) {
            throw new IllegalArgumentException("Room cannot be null for a booking");
        }
        BookingEntity save = bookingRepository.saveAndFlush(bookingMapper.toEntity(booking));
        return bookingMapper.toDto(save);
    }

    public void saveBooking(Integer roomId, BookingDto bookingDto, UUID userId) {
        RoomDto room = roomService.getRoomById(roomId);
        Optional<UserDto> optionalUser = userService.getById(userId);

        if (optionalUser.isPresent() && room != null && room.getRoomStatus() == RoomStatus.AVAILABLE) {
            UserDto user = optionalUser.get();

            bookingDto.setUser(user);
            bookingDto.setRoom(room);

            save(bookingDto);

            room.setRoomStatus(RoomStatus.BOOKED);
            roomService.updateRoom(room.getRoomId(), room);
        } else {
            System.out.println("Room is not available for booking");
        }
    }


}

