package com.example.mainfile.mapper;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.RoomEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class RoomMapperTest {

    @InjectMocks
    private RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @Test
    public void testEntityToDtoMapping() {
        RoomEntity roomEntity = new RoomEntity();
        RoomDto roomDto = roomMapper.toDto(roomEntity);
        assertEquals(roomEntity.getRoomId(), roomDto.getRoomId());
    }
}



