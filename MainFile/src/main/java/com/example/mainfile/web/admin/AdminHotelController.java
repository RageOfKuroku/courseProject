package com.example.mainfile.web.admin;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/hotels")
public class AdminHotelController {

    private final HotelService service;

    @GetMapping("/addHotel")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDto());
        model.addAttribute("hotels", service.getAllHotels());
        return "adminPageHotels";
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
    public String addHotel(@ModelAttribute("hotel") HotelDto hotelDto, RedirectAttributes redirectAttributes,
                           @RequestParam(value = "file") MultipartFile file) throws IOException {
        if (hotelDto.getStars() < 1 || hotelDto.getStars() > 5) {
            redirectAttributes.addFlashAttribute("error", "Звёзды отеля должны быть в диапазоне от 1 до 5");
            return "redirect:/admin/hotels/addHotel";
        }
        hotelDto.setImageToShow(file.getBytes());
        service.createHotel(hotelDto);
        redirectAttributes.addFlashAttribute("message", "Отель успешно добавлен!");
        return "redirect:/admin/hotels/addHotel";
    }

    @PostMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Integer id) {
        service.deleteHotel(id);
        return "redirect:/admin/hotels/addHotel";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        HotelDto hotelDto = service.getHotelById(id);
        model.addAttribute("hotel", hotelDto);
        return "adminHotelUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateHotel(@PathVariable("id") Integer id, @ModelAttribute("hotel") HotelDto hotelDto,
                              @RequestParam("file") MultipartFile file) throws IOException {
        if (hotelDto.getStars() < 1 || hotelDto.getStars() > 5) {
            return "redirect:/admin/hotels/addHotel";
        }
        if (!file.isEmpty()) {
            byte[] imageBytes = file.getBytes();
            hotelDto.setImageToShow(imageBytes);
        }
        hotelDto.setName(hotelDto.getName());
        hotelDto.setAddress(hotelDto.getAddress());
        hotelDto.setStars(hotelDto.getStars());
        service.updateHotel(id, hotelDto);
        return "redirect:/admin/hotels/addHotel";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchName") String searchName, @RequestParam("searchAddress") String searchAddress, Model model) {
        List<HotelDto> hotels = service.searchHotels(searchName, searchAddress);
        model.addAttribute("hotels", hotels);
        model.addAttribute("hotel", new HotelDto());
        return "adminPageHotels";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sortStars") String sortStars, Model model) {
        List<HotelDto> hotels;
        if ("asc".equals(sortStars)) {
            hotels = service.sortHotelsAscending();
        } else {
            hotels = service.sortHotelsDescending();
        }
        model.addAttribute("hotels", hotels);
        model.addAttribute("hotel", new HotelDto());
        return "adminPageHotels";
    }
}
