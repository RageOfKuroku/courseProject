package com.example.mainfile.service;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public void save(BookingDto booking) {
        if (booking.getRoom() == null) {
            throw new IllegalArgumentException("RoomId cannot be null for a booking");
        }
        BookingEntity bookingEntity = bookingMapper.toEntity(booking);
        bookingRepository.save(bookingEntity);
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


    public List<BookingDto> getBookingsForUser(UUID userId) {
        List<BookingEntity> bookings = bookingRepository.findAllByUserId(userId);
        return bookingMapper.toListDto(bookings);
    }


}





