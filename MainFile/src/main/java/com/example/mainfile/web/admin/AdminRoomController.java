package com.example.mainfile.web.admin;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.RoomService;
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
        model.addAttribute("hotelRooms", rooms);
        model.addAttribute("hotelId", hotel.getId());
        return "adminPageRooms";
    }

    @PostMapping("/add/{id}")
    public String addRoom(@PathVariable Integer id, @ModelAttribute RoomDto room, @RequestParam(value = "file") MultipartFile file) throws IOException {
        if (room.getRoomPrice() < 0) {
            return "redirect:/admin/hotels/rooms/" + id;
        }
        HotelDto hotel = hotelService.getHotelById(id);
        room.setHotel(hotel);
        room.setImageToShow(file.getBytes());
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


    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        RoomDto room = service.getRoomById(id);
        Integer hotelId = room.getHotel().getId();
        service.deleteRoom(id);
        redirectAttributes.addFlashAttribute("message", "Комната успешно удалена!");
        return "redirect:/admin/hotels/rooms/" + hotelId;
    }

    @PostMapping("/updateRoom")
    public String updateRoom(@ModelAttribute RoomDto room, @RequestParam("file") MultipartFile file) throws IOException {
        if (room.getRoomPrice() < 0) {
            return "redirect:/admin/hotels/rooms/" + room.getHotel().getId();
        }
        RoomDto existingRoom = service.getRoomById(room.getRoomId());
        if (!file.isEmpty()) {
            byte[] imageBytes = file.getBytes();
            existingRoom.setImageToShow(imageBytes);
        }
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setRoomStatus(room.getRoomStatus());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setRoomPrice(room.getRoomPrice());

        service.updateRoom(existingRoom.getRoomId(),existingRoom);

        return "redirect:/admin/hotels/rooms/" + existingRoom.getHotel().getId();
    }





}