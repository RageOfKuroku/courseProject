package com.example.mainfile.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private Model model;

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleUsernameNotFoundException() {
        String expectedErrorMessage = "User not found";
        UsernameNotFoundException exception = new UsernameNotFoundException(expectedErrorMessage);

        when(model.addAttribute("error", expectedErrorMessage)).thenReturn(model);

        String viewName = globalExceptionHandler.handleUsernameNotFoundException(exception, model);

        assertEquals("loginPage", viewName);
    }
}

