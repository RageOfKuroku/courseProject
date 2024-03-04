package com.example.mainfile.web;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.filter.TokenGenFilter;
import com.example.mainfile.filter.TokenValidFilter;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.service.*;
import com.example.mainfile.web.user.HotelController;
import org.hibernate.validator.cfg.defs.UUIDDef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private HotelController hotelController;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private UserService userService;

    @MockBean
    private BookingService bookingService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenGenFilter tokenGenFilter;

    @MockBean
    private TokenValidFilter tokenValidFilter;


    @Test
    public void testShowHotelForm() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(1);

        when(hotelService.getAllHotels()).thenReturn(Arrays.asList(hotelDto));
        when(userService.getById(userEntity.getId())).thenReturn(Optional.of(new UserDto()));

        Authentication auth = new UsernamePasswordAuthenticationToken(userEntity, null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/hotels")
                        .principal(() -> "user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSortByStars() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(1);
        hotelDto.setStars(5);

        Authentication auth = new UsernamePasswordAuthenticationToken(userEntity, null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(hotelService.getHotelsByStars(5)).thenReturn(List.of(hotelDto));

        mockMvc.perform(get("/hotels/sortByStars")
                        .param("stars", "5")
                        .principal(() -> "user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearch() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(1);

        Authentication auth = new UsernamePasswordAuthenticationToken(userEntity, null, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(hotelService.searchHotelsWithoutAddress("query")).thenReturn(Arrays.asList(hotelDto));

        mockMvc.perform(get("/hotels/search")
                        .param("query", "query")
                        .principal(() -> "user"))
                .andExpect(status().isOk());
    }

}

