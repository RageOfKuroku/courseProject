package com.example.mainfile.mapper;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.entity.BookingEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class BookingMapperTest {

    @InjectMocks
    private BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    @Test
    public void testEntityToDtoMapping() {
        BookingEntity bookingEntity = new BookingEntity();
        BookingDto bookingDto = bookingMapper.toDto(bookingEntity);
        assertEquals(bookingEntity.getId(), bookingDto.getId());
    }

    @Test
    public void testDtoToEntityMapping() {
        BookingDto bookingDto = new BookingDto();
        BookingEntity bookingEntity = bookingMapper.toEntity(bookingDto);
        assertEquals(bookingDto.getId(), bookingEntity.getId());
    }
}

