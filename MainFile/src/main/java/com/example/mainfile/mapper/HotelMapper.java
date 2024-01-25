package com.example.mainfile.mapper;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.entity.HotelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(target = "hotelId", source = "hotelId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageToShow", source = "imageToShow")
    HotelEntity toEntity(HotelDto dto);
    @Mapping(target = "hotelId", source = "hotelId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageToShow", source = "imageToShow")
    HotelDto toDto(HotelEntity entity);

    @Mapping(target = "hotelId", ignore = true)
    @Mapping(target = "imageToShow", ignore = true)
    void update(@MappingTarget HotelEntity entity, HotelDto dto);
    List<HotelEntity> toListEntity(List<HotelDto> dtos);

    List<HotelDto> toListDto(List<HotelEntity> entities);
}
