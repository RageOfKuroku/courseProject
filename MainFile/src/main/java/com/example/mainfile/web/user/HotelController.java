package com.example.mainfile.web.user;

import com.example.mainfile.dto.*;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showHotelDetails(@PathVariable Integer id, Model model, @AuthenticationPrincipal UserEntity user) {
        HotelDto hotel = service.getHotelById(id);
        model.addAttribute("hotel", hotel);
        RoomDto room = RoomDto.builder().hotel(hotel).build();
        model.addAttribute("room", room);
        List<RoomDto> rooms = roomService.getRoomsByHotelId(id);
        model.addAttribute("hotelRooms", rooms);
        model.addAttribute("user", user);
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
    public String bookRoom(@RequestParam Integer roomId, @ModelAttribute BookingDto bookingDto, @AuthenticationPrincipal UserEntity userEntity, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (userEntity != null) {
            Optional<UserDto> optionalUser = userService.getById(userEntity.getId());

            if (optionalUser.isPresent()) {
                UserDto user = optionalUser.get();
                bookingDto.setUser(user);

                if (bookingDto.getStartDate().isAfter(bookingDto.getEndDate())) {
                    redirectAttributes.addFlashAttribute("error", "Дата начала не может быть позже даты окончания");
                    redirectAttributes.addFlashAttribute("roomId", roomId);
                    return "redirect:/hotels";
                }

                bookingService.saveBooking(roomId, bookingDto, user.getId());
            }
        }

        return "redirect:/hotels";
    }



    @GetMapping("/sortByStars")
    public String sortByStars(@RequestParam Integer stars, Model model, @AuthenticationPrincipal UserEntity user) {
        List<HotelDto> hotels = service.getHotelsByStars(stars);

        Map<Integer, Long> reviewsCount = new HashMap<>();

        for (HotelDto hotel : hotels) {
            Long count = reviewRepository.countByHotelId(hotel.getId());
            reviewsCount.put(hotel.getId(), count );
        }

        model.addAttribute("hotels", hotels);
        model.addAttribute("user", user);
        model.addAttribute("reviewsCount", reviewsCount);
        model.addAttribute("selectedStars", stars);

        return "hotelsPage";
    }



    @GetMapping("/search")
    public String search(@RequestParam String query, Model model, @AuthenticationPrincipal UserEntity user) {
        List<HotelDto> hotels = service.searchHotelsWithoutAddress(query);

        Map<Integer, Long> reviewsCount = new HashMap<>();

        for (HotelDto hotel : hotels) {
            Long count = reviewRepository.countByHotelId(hotel.getId());
            reviewsCount.put(hotel.getId(), count );
        }

        model.addAttribute("hotels", hotels);
        model.addAttribute("user", user);
        model.addAttribute("reviewsCount", reviewsCount);
        return "hotelsPage";
    }

}
