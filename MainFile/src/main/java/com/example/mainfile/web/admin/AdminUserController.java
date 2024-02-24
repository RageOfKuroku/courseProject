package com.example.mainfile.web.admin;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.service.BookingService;
import com.example.mainfile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final BookingService bookingService;
    @GetMapping
    public String showPage(Model model, Authentication authentication) {
        List<UserDto> users = userService.findAll();
        for (UserDto user : users) {
            user.setBookings(bookingService.getBookingsForUser(user.getId()));
        }
        model.addAttribute("users", users);
        return "adminPageUsers";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }




}
