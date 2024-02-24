package com.example.mainfile.service;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.exception.ResourceNotFoundException;
import com.example.mainfile.mapper.HotelMapper;
import com.example.mainfile.repository.HotelRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Getter
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

    public HotelDto createHotel(HotelDto hotel) {
        HotelEntity save = hotelRepository.save(hotelMapper.toEntity(hotel));
        return hotelMapper.toDto(save);
    }

    public HotelDto updateHotel(Integer id, HotelDto dto) {
        if(id != null){
            HotelEntity hotelEntity = hotelRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel with this ID not found"));
            hotelMapper.update(hotelEntity, dto);
            hotelRepository.save(hotelEntity);
            return dto;
        }else {
            throw new ResourceNotFoundException("Hotel with this ID not found");
        }
    }

    @Transactional
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
}
