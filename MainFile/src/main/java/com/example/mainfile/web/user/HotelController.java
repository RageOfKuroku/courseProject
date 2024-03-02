package com.example.mainfile.web.user;

import com.example.mainfile.dto.*;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Controller
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService service;
    private final RoomService roomService;
    private final UserService userService;
    private final BookingService bookingService;
    private final ReviewRepository reviewRepository;



    @GetMapping
    public String showHotelForm(Model model, @AuthenticationPrincipal UserEntity userEntity) {
        model.addAttribute("hotel", new HotelDto());
        List<HotelDto> hotels = service.getAllHotels();
        model.addAttribute("hotels", hotels);

        Map<Integer, Long> reviewsCount = new HashMap<>();
        Map<Integer, List<RoomDto>> hotelRooms = new HashMap<>();
        for (HotelDto hotel : hotels) {
            Long count = reviewRepository.countByHotelId(hotel.getId());
            reviewsCount.put(hotel.getId(), count);
            List<RoomDto> rooms = roomService.getRoomsByHotelId(hotel.getId());
            hotelRooms.put(hotel.getId(), rooms);
        }
        model.addAttribute("reviewsCount", reviewsCount);
        model.addAttribute("room", hotelRooms);

        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());
            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                model.addAttribute("user", user);
            }
        }

        return "hotelsPage";
    }



    @GetMapping("/details/{id}")
    public String showHotelDetails(@PathVariable Integer id, Model model) {
        HotelDto hotel = service.getHotelById(id);
        List<RoomEntity> rooms = roomService.getRoomEntitiesByHotelId(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        return "hotelPage";
    }


    @PostMapping("/update/{id}")
    public String updateHotelDetails(@PathVariable Integer id, @ModelAttribute HotelDto updatedHotel, Model model) {
        service.updateHotel(id, updatedHotel);
        HotelDto hotel = service.getHotelById(id);
        List<RoomDto> rooms = roomService.getRoomsByHotelId(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        return "redirect:/hotels/" + id;
    }

    @PostMapping("/bookRoom")
    public String bookRoom(@RequestParam Integer roomId, @ModelAttribute BookingDto bookingDto, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());

            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                bookingDto.setUser(user);

                
                bookingService.saveBooking(roomId, bookingDto, user.getId());
            }
        }

        return "redirect:/hotels";
    }



}
