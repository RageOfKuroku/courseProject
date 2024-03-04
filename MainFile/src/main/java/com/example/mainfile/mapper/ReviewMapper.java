package com.example.mainfile.mapper;

import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "hotel", source = "hotel")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "impressions", source = "impressions")
    ReviewDto toDto(ReviewEntity entity);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "hotel", source = "hotel")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "impressions", source = "impressions")
    ReviewEntity toEntity(ReviewDto dto);

    List<ReviewEntity> toListEntity(List<ReviewDto> dtos);

    List<ReviewDto> toListDto(List<ReviewEntity> entities);
}




