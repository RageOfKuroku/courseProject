package com.example.mainfile.web;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.filter.TokenGenFilter;
import com.example.mainfile.filter.TokenValidFilter;
import com.example.mainfile.model.Role;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.service.*;
import com.example.mainfile.web.additions.ReviewController;
import com.example.mainfile.web.user.HotelController;
import org.hibernate.validator.cfg.defs.UUIDDef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
@WithMockUser(username = "admin", authorities = {"ADMIN"})
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenGenFilter tokenGenFilter;

    @MockBean
    private TokenValidFilter tokenValidFilter;

    @MockBean
    private ReviewController reviewController;

    @Mock
    private Model model;
    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void testShowReviews() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(1);

        when(reviewService.getReviewsByHotelId(anyInt())).thenReturn(List.of(reviewDto));
        when(hotelService.getHotelEntityById(anyInt())).thenReturn(new HotelEntity());

        mockMvc.perform(get("/reviews/hotel/1"))
                .andExpect(status().isOk());
    }


}


