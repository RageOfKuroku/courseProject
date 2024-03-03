package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.mapper.ReviewMapper;
import com.example.mainfile.model.Role;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelMapper hotelMapper;

    public List<ReviewDto> getReviewsByHotelId(Integer hotelId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByHotel(hotelMapper.toEntity(hotelService.getHotelById(hotelId)));
        return reviewMapper.toListDto(reviewEntities);
    }

    public String getHotelNameById(Integer hotelId) {
        return hotelRepository.findById(hotelId)
                .map(HotelEntity::getName)
                .orElse("Неизвестный отель");
    }

    public void addReview(ReviewDto reviewDto, UserEntity user, Integer hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        ReviewEntity existingReview = reviewRepository.findByUserAndHotel(user, hotel);
        if (existingReview != null) {
            existingReview.setRating(reviewDto.getRating());
            existingReview.setImpressions(reviewDto.getImpressions());
            reviewRepository.save(existingReview);
        } else {
            ReviewEntity newReview = new ReviewEntity();
            newReview.setUser(user);
            newReview.setHotel(hotel);
            newReview.setRating(reviewDto.getRating());
            newReview.setImpressions(reviewDto.getImpressions());
            reviewRepository.save(newReview);
        }

        hotelService.calculateRating(hotelId);
    }

    public Integer deleteReview(Integer reviewId, UserEntity currentUser) {
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        Integer hotelId = review.getHotel().getId();
        if (review.getUser().equals(currentUser) || currentUser.getRole() == Role.ADMIN) {
            reviewRepository.delete(review);
        }

        hotelService.calculateRating(hotelId);

        return hotelId;
    }









}

