package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.RoomEntity;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.repository.BookingRepository;
import com.example.mainfile.repository.HotelRepository;
import com.example.mainfile.repository.ReviewRepository;
import com.example.mainfile.repository.RoomRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Getter
@Transactional
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public HotelDto getHotelById(Integer id) {
        return hotelMapper.toDto(hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with this ID not found")));
    }

    public List<HotelDto> getAllHotels() {

        return hotelMapper.toListDto(hotelRepository.findAll());
    }

    public void createHotel(HotelDto hotel) {
        HotelEntity save = hotelRepository.save(hotelMapper.toEntity(hotel));
        hotelMapper.toDto(save);
    }

    public void updateHotel(Integer id, HotelDto dto) {
        if(id != null){
            HotelEntity hotelEntity = hotelRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel with this ID not found"));
            if(dto.getImageToShow() != null) {
                hotelMapper.update(hotelEntity, dto);
            } else {
                hotelMapper.updateWithoutImage(hotelEntity, dto);
            }
            hotelRepository.save(hotelEntity);
        }else {
            throw new ResourceNotFoundException("Hotel with this ID not found");
        }
    }

    public void deleteHotel(Integer hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        List<ReviewEntity> reviews = reviewRepository.findByHotel(hotel);
        List<RoomEntity> rooms = roomRepository.findByHotel(hotel);
        List<BookingEntity> bookings = bookingRepository.findByRoomIn(rooms);

        bookingRepository.deleteAll(bookings);

        reviewRepository.deleteAll(reviews);

        roomRepository.deleteAll(rooms);

        hotelRepository.delete(hotel);
    }
    public List<HotelDto> searchHotels(String searchName, String searchAddress) {
        return hotelMapper.toListDto(hotelRepository.findByNameContainingAndAddressContaining(searchName, searchAddress));
    }

    public List<HotelDto> searchHotelsWithoutAddress(String query) {
        List<HotelEntity> hotelEntities = hotelRepository.findByNameContaining(query);
        return hotelMapper.toListDto(hotelEntities);
    }

    public List<HotelDto> sortHotelsAscending() {
        return hotelMapper.toListDto(hotelRepository.findAll(Sort.by(Sort.Direction.ASC, "rating")));
    }

    public List<HotelDto> sortHotelsDescending() {
        return hotelMapper.toListDto(hotelRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")));
    }

    public HotelEntity getHotelEntityById(Integer hotelId) {
        HotelDto hotelDto = getHotelById(hotelId);
        return hotelMapper.toEntity(hotelDto);
    }

    public void calculateRating(Integer hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        List<ReviewEntity> reviews = reviewRepository.findByHotel(hotel);
        double averageRating = reviews.stream().mapToDouble(ReviewEntity::getRating).average().orElse(0.0);
        hotel.setRating(averageRating);
        hotelRepository.save(hotel);
    }

    public List<HotelDto> getHotelsByStars(Integer stars) {
        List<HotelEntity> hotelEntities = hotelRepository.findByStars(stars);
        return hotelMapper.toListDto(hotelEntities);
    }




}
