package com.example.mainfile.web.auth;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService service;

    @GetMapping
    public ModelAndView regPage(@ModelAttribute("newUser") UserDto user) {
        return new ModelAndView("registrationPage");
    }

    @PostMapping("/save")
    public ModelAndView validPage(@Valid @ModelAttribute("newUser") UserDto user,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "checkPassword") String checkPass) {
        var modelAndView = new ModelAndView("registrationPage");
        if (!bindingResult.hasFieldErrors()) {
            if (user.getPassword().equals(checkPass)) {
                if(!service.isExistsInDb(user)){
                    service.save(user);
                    return new ModelAndView("redirect:/user/login");
                }
                else {
                    modelAndView.addObject("isExists", true);
                }
            }else {
                modelAndView.addObject("check", false);
            }
        } else {
            modelAndView.addObject("check", false);
        }
        return modelAndView;
    }

}





