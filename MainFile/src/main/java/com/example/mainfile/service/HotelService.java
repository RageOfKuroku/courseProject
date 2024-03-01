package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.repository.HotelRepository;
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

    public HotelDto getHotelById(Integer id) {
        HotelEntity hotelNotFound = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with this ID not found"));
        return hotelMapper.toDto(hotelNotFound);
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

    public void deleteHotel(Integer id) {
        if(id != null) {
            hotelRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("Hotel with this ID not found");
        }
    }

    public void deleteAll(){
        hotelRepository.deleteAll();
    }

    public List<HotelDto> searchHotels(String searchName, String searchAddress) {
        return hotelMapper.toListDto(hotelRepository.findByNameContainingAndAddressContaining(searchName, searchAddress));
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
}
