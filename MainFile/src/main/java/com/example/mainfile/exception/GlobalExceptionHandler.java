package com.example.mainfile.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "loginPage";
    }
}
