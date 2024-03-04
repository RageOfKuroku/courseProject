package com.example.mainfile.mapper;

import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.entity.ReviewEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ReviewMapperTest {

    @InjectMocks
    private ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    public void testEntityToDtoMapping() {
        ReviewEntity reviewEntity = new ReviewEntity();
        ReviewDto reviewDto = reviewMapper.toDto(reviewEntity);
        assertEquals(reviewEntity.getUser(), reviewDto.getUser());
    }

    @Test
    public void testDtoToEntityMapping() {
        ReviewDto reviewDto = new ReviewDto();
        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewDto);
        assertEquals(reviewDto.getUser(), reviewEntity.getUser());
    }
}

