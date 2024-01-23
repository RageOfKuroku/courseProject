package com.example.mainfile.web;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class HotelController {
    private final HotelRepository repository;
    // constructor with HotelService

    @GetMapping
    public List<HotelDto> getAllHotels() {
        // implementation
    }

    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        // implementation
    }

    @PostMapping
    public HotelDto addHotel(@RequestBody HotelDto hotelDto) {
        // implementation
    }

    @PutMapping("/{id}")
    public HotelDto updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        // implementation
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Long id) {
        // implementation
    }
}
