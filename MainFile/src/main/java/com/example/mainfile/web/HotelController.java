package com.example.mainfile.web;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService service;
    @GetMapping
    public String showHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDto());
        model.addAttribute("hotels", service.getAllHotels());
        return "hotelsPage";
    }

    @PostMapping("/hotels/{id}")
    public String showHotelDetails(@PathVariable Integer id, Model model) {
        HotelDto hotel = service.getHotelById(id);
        model.addAttribute("hotel", hotel);
        return "hotelPage";
    }
}
