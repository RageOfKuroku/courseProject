package com.example.mainfile.web.user;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public String showForm(Model model, @AuthenticationPrincipal UserEntity user) {
        Optional<UserDto> optionalUserDto = userService.getById(user.getId());
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            model.addAttribute("bookings", user.getBookings());
            model.addAttribute("user", userDto);
        }
        return "customerPage";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto, @AuthenticationPrincipal UserEntity user) {
        if (user.getId() != null) {
            userDto.setId(user.getId());
            userService.update(userDto);
            return "redirect:/user/profile";
        } else {
            throw new ResourceNotFoundException("User with this id does not exist");
        }
    }






}
