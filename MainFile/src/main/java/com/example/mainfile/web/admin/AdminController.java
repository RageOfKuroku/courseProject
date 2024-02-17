package com.example.mainfile.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class AdminController {
    @GetMapping("/admin")
    public String showMainPage(Authentication authentication) {
        return "adminMainPage";
    }
}
