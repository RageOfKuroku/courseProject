package com.example.mainfile.web.auth;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.model.CurrentUser;
import com.example.mainfile.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService service;

    @GetMapping
    public ModelAndView getMainPage(@ModelAttribute("newUser") UserDto dto) {
        return new ModelAndView("loginPage");
    }

    @PostMapping("/submit")
    public ModelAndView enterData(@Valid @ModelAttribute("newUser") UserDto dto,
                                  BindingResult result, HttpSession session) {
        if (!result.hasFieldErrors()) {
            if (service.isExistsInDb(dto)) {
                var model = new ModelAndView("loginPage");
                model.addObject("notFound", false);
                return model;
            } else {
                Optional<UserEntity> user = service.findByPasswordAndEmail(dto);
                if (user.isPresent()) {
                    session.setAttribute("userId", user.get().getId());
                    if (user.get().getCustomer() != null) {
                        session.setAttribute("customerId", user.get().getCustomer().getCustomerId());
                    }
                    CurrentUser.entity = user.get();
                    return new ModelAndView("redirect:/hotels");
                }
            }
        }
        return new ModelAndView("loginPage");
    }


}
