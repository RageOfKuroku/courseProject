package com.example.mainfile.web.user;

import com.example.mainfile.dto.*;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@Controller
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService service;
    private final RoomService roomService;
    private final UserService userService;
    private final BookingService bookingService;


    @GetMapping
    public String showHotelForm(Model model, @AuthenticationPrincipal UserEntity userEntity) {
        model.addAttribute("hotel", new HotelDto());
        model.addAttribute("hotels", service.getAllHotels());

        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());
            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                model.addAttribute("user", user);
            }
        }

        return "hotelsPage";
    }

    @GetMapping("/{id}")
    public String showHotelDetails(@PathVariable Integer id, Model model) {
        HotelDto hotel = service.getHotelById(id);
        List<RoomDto> rooms = roomService.getRoomsByHotelId(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        return "hotelPage";
    }

    @PostMapping("/{id}")
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
