package com.example.mainfile.web.user;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.service.BookingService;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.RoomService;
import com.example.mainfile.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService service;
    private final RoomService roomService;
    private final UserService userService;
    private final BookingService bookingService;
    @GetMapping
    public String showHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDto());
        model.addAttribute("hotels", service.getAllHotels());
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

    @PostMapping("/bookRoom")
    public String bookRoom(@ModelAttribute BookingDto bookingDto, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId != null) {

            RoomDto room = roomService.getRoomById(bookingDto.getRoom().getRoomId());
            Optional<UserDto> optionalUser = userService.getById(userId);

            if (optionalUser.isPresent() && room != null && room.getRoomStatus() == RoomStatus.AVAILABLE) {
                UserDto user = optionalUser.get();


                bookingDto.setCustomer(user.getCustomer());
                bookingDto.setRoom(room);


                bookingService.save(bookingDto);


                room.setRoomStatus(RoomStatus.BOOKED);
                roomService.updateRoom(room.getRoomId(),room);
            }
        }
        return "redirect:/hotels";
    }








}
