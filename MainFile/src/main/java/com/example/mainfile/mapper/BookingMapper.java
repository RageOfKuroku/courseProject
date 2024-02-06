package com.example.mainfile.mapper;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    BookingEntity toEntity(BookingDto dto);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    BookingDto toDto(BookingEntity entity);
}
