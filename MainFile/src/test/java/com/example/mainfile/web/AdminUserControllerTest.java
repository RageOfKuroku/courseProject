package com.example.mainfile.web;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.service.BookingService;
import com.example.mainfile.service.UserService;
import com.example.mainfile.web.admin.AdminUserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ADMIN"})
public class AdminUserControllerTest {

    @InjectMocks
    private AdminUserController adminUserController;

    @MockBean
    private UserService userService;

    @MockBean
    private BookingService bookingService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        adminUserController = new AdminUserController(userService, bookingService);
    }
    @Test
    public void testShowPage() {
        UserDto user = new UserDto();
        user.setId(UUID.randomUUID());
        when(userService.findAll()).thenReturn(Collections.singletonList(user));
        when(bookingService.getBookingsForUser(user.getId())).thenReturn(Collections.emptyList());

        String viewName = adminUserController.showPage(model, authentication);

        verify(userService, times(1)).findAll();
        verify(bookingService, times(1)).getBookingsForUser(user.getId());
        verify(model, times(1)).addAttribute(eq("users"), anyList());
        assertEquals("adminPageUsers", viewName);
    }

    @Test
    public void testDeleteUser() {
        UUID id = UUID.randomUUID();

        String viewName = adminUserController.deleteUser(id);

        verify(userService, times(1)).deleteById(id);
        assertEquals("redirect:/admin/users", viewName);
    }
}

