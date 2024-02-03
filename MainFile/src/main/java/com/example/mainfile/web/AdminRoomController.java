package com.example.mainfile.web;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/hotels/rooms")
public class AdminRoomController {

    private final HotelService hotelService;
    private final RoomService service;

    @GetMapping("/{id}")
    public String showRoomsPage(@PathVariable Integer id, Model model) {
        HotelDto hotel = hotelService.getHotelById(id);
        RoomDto room = RoomDto.builder().hotel(hotel).build();
        model.addAttribute("room", room);

        List<RoomDto> rooms = service.getRoomsByHotelId(id);
        model.addAttribute("rooms", rooms);

        model.addAttribute("hotelId", hotel.getHotelId());
        return "adminPageRooms";
    }


    @PostMapping("/add")
    public String addRoom(@ModelAttribute RoomDto room) {
        service.addRoom(room);
        return "redirect:/admin/hotels/rooms/" + room.getHotel().getHotelId();
    }


    @PostMapping("/add/{id}")
    public String addRoom(@PathVariable Integer id, @ModelAttribute RoomDto room) {
        HotelDto hotel = hotelService.getHotelById(id);
        room.setHotel(hotel);
        service.addRoom(room);
        return "redirect:/admin/hotels/rooms/" + id;
    }


    @GetMapping
    public List<RoomDto> getAllRooms() {
        return service.getAllRooms();
    }

    @GetMapping("/getRoomById/{id}")
    public RoomDto getRoomById(@PathVariable Integer id) {
        return service.getRoomById(id);
    }

    @PutMapping("/{id}")
    public RoomDto updateRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        return service.updateRoom(id, roomDto);
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        RoomDto room = service.getRoomById(id);
        Integer hotelId = room.getHotel().getHotelId();
        service.deleteRoom(id);
        redirectAttributes.addFlashAttribute("message", "Комната успешно удалена!");
        return "redirect:/admin/hotels/rooms/" + hotelId;
    }


}


