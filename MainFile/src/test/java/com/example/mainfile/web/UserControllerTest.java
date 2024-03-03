package com.example.mainfile.web;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.model.Role;
import com.example.mainfile.service.BookingService;
import com.example.mainfile.service.TokenService;
import com.example.mainfile.service.UserService;
import com.example.mainfile.web.user.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private BCryptPasswordEncoder encoder;



    @Test
    public void testShowForm() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName("Test User");
        userDto.setEmail("testuser@example.com");
        userDto.setRole(Role.ADMIN);

        when(userService.getById(any())).thenReturn(Optional.of(userDto));

        mockMvc.perform(get("/user/profile")
                        .with(user(new UserEntity())))
                .andExpect(status().isOk())
                .andExpect(view().name("customerPage"))
                .andExpect(model().attributeExists("bookings"))
                .andExpect(model().attributeExists("user"));
    }
}



