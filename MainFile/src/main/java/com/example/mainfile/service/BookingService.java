package com.example.mainfile.service;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.mapper.BookingMapper;
import com.example.mainfile.repository.BookingRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingDto save(BookingDto booking) {
        BookingEntity save = bookingRepository.save(bookingMapper.toEntity(booking));
        return bookingMapper.toDto(save);
    }
}

