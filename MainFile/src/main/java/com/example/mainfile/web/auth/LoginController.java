package com.example.mainfile.web.auth;

import com.example.mainfile.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@RequiredArgsConstructor
@Controller
@RequestMapping("/auth/login")
public class LoginController {
    @GetMapping
    public ModelAndView getMainPage(@ModelAttribute("newUser") UserDto dto) {
        return new ModelAndView("loginPage");
    }
}

