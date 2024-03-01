package com.example.mainfile.service;

import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.mapper.ReviewMapper;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRepository userRepository;

    public List<ReviewDto> getReviewsByHotelId(Integer hotelId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByHotel(hotelMapper.toEntity(hotelService.getHotelById(hotelId)));
        return reviewMapper.toListDto(reviewEntities);
    }

    public String getHotelNameById(Integer hotelId) {
        return hotelRepository.findById(hotelId)
                .map(HotelEntity::getName)
                .orElse("Неизвестный отель");
    }

    public void addReview(ReviewDto reviewDto, UserEntity user) {
        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewDto);
        reviewEntity.setUser(user);
        reviewRepository.save(reviewEntity);
    }

}

