package com.example.mainfile.mapper;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class HotelMapperTest {

    @InjectMocks
    private HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Test
    public void testEntityToDtoMapping() {
        HotelEntity hotelEntity = new HotelEntity();
        HotelDto hotelDto = hotelMapper.toDto(hotelEntity);
        assertEquals(hotelEntity.getId(), hotelDto.getId());
    }
}
