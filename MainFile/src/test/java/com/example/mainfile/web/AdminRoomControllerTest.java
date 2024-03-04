package com.example.mainfile.web;

import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.service.RoomService;
import com.example.mainfile.web.admin.AdminRoomController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"ADMIN"})
public class AdminRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private AdminRoomController adminRoomController;

    @Test
    public void testShowRoomsPage() throws Exception {
        mockMvc.perform(get("/admin/hotels/rooms/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPageRooms"));
    }

    @Test
    public void testAddRoom() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", "text/plain", "Hello, World!".getBytes());
        mockMvc.perform(multipart("/admin/hotels/rooms/add/{id}", 1)
                        .file(file)
                        .param("roomPrice", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/hotels/rooms/1"));
    }

}

