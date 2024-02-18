package com.example.mainfile.web.user;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public String showForm(@PathVariable UUID userId, Model model) {
        Optional<UserDto> optionalUserDto = userService.getById(userId);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            model.addAttribute("user", userDto);
        }
        return "customerPage";
    }


    @PostMapping("/{userId}/updateUser")
    public String updateUser(@PathVariable UUID userId, @ModelAttribute("user") UserDto userDto) {
        if (userId != null) {
            userDto.setId(userId);
            userService.update(userDto);
            return "redirect:/user/" + userDto.getId();
        } else {
            throw new ResourceNotFoundException("User with this id does not exist");
        }
    }



    @GetMapping("/{userId}/bookings")
    public List<BookingDto> getBookings(@PathVariable UUID userId) {
        return userService.getBookings(userId);
    }


}
