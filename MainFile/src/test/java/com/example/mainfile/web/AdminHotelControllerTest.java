package com.example.mainfile.web;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.filter.TokenGenFilter;
import com.example.mainfile.filter.TokenValidFilter;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.TokenService;
import com.example.mainfile.web.admin.AdminHotelController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ADMIN"})
public class AdminHotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenGenFilter tokenGenFilter;

    @Autowired
    private TokenValidFilter tokenValidFilter;

    @Test
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public void testShowAddHotelForm() throws Exception {
        mockMvc.perform(get("/admin/hotels/addHotel"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPageHotels"))
                .andExpect(model().attributeExists("hotel", "hotels"));
    }

    @Test
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public void testAddHotel() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());
        mockMvc.perform(multipart("/admin/hotels/addHotel").file(file)
                        .param("stars", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/hotels/addHotel"));
    }


}


