package com.example.mainfile.web;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class HotelController {

    private final HotelService service;

    @GetMapping("/addHotel")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDto());
        model.addAttribute("hotels", service.getAllHotels());
        return "adminPage";
    }
    @GetMapping
    public List<HotelDto> getAllHotels() {
        return service.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Integer id) {
        return service.getHotelById(id);
    }

    @PostMapping("/addHotel")
    public String addHotelRedirection(@ModelAttribute("hotel") HotelDto hotelDto, RedirectAttributes redirectAttributes) {
        service.createHotel(hotelDto);
        redirectAttributes.addFlashAttribute("message", "Отель успешно добавлен!");
        return "redirect:/admin/addHotel";
    }

    @PutMapping("/{id}")
    public HotelDto updateHotel(@PathVariable Integer id, @RequestBody HotelDto hotelDto) {
        return service.updateHotel(id, hotelDto);
    }

    @PostMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        service.deleteHotel(id);
        redirectAttributes.addFlashAttribute("message", "Отель успешно удалён!");
        return "redirect:/admin/addHotel";
    }

}
