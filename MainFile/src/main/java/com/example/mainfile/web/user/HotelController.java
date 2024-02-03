package com.example.mainfile.web.user;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService service;
    private final RoomService roomService;
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

}
