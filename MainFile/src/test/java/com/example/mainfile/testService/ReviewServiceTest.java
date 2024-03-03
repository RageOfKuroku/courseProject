package com.example.mainfile.testService;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.mapper.ReviewMapper;
import com.example.mainfile.model.Role;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelService hotelService;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testGetReviewsByHotelId() {
        Integer hotelId = 1;
        HotelDto hotelDto = new HotelDto();
        List<ReviewEntity> reviewEntities = new ArrayList<>();

        when(hotelService.getHotelById(hotelId)).thenReturn(hotelDto);
        when(hotelMapper.toEntity(hotelDto)).thenReturn(new HotelEntity());
        when(reviewRepository.findByHotel(any(HotelEntity.class))).thenReturn(reviewEntities);

        reviewService.getReviewsByHotelId(hotelId);

        verify(reviewMapper, times(1)).toListDto(reviewEntities);
    }

    @Test
    public void testGetHotelNameById() {
        Integer hotelId = 1;
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setName("Test Hotel");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotelEntity));

        String hotelName = reviewService.getHotelNameById(hotelId);

        assertEquals("Test Hotel", hotelName);
    }

    @Test
    public void testAddReview() {
        Integer hotelId = 1;
        ReviewDto reviewDto = new ReviewDto();
        UserEntity user = new UserEntity();
        HotelEntity hotelEntity = new HotelEntity();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotelEntity));
        when(reviewRepository.findByUserAndHotel(user, hotelEntity)).thenReturn(null);

        reviewService.addReview(reviewDto, user, hotelId);

        verify(reviewRepository, times(1)).save(any(ReviewEntity.class));
        verify(hotelService, times(1)).calculateRating(hotelId);
    }

    @Test
    public void testDeleteReview() {
        Integer reviewId = 1;
        UserEntity currentUser = new UserEntity();
        currentUser.setRole(Role.ADMIN);
        ReviewEntity review = new ReviewEntity();
        review.setUser(currentUser);
        HotelEntity hotel = new HotelEntity();
        hotel.setId(1);
        review.setHotel(hotel);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteReview(reviewId, currentUser);

        verify(reviewRepository, times(1)).delete(review);
        verify(hotelService, times(1)).calculateRating(hotel.getId());
    }

}

