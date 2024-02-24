package com.example.mainfile.mapper;

import com.example.mainfile.dto.BookingDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.repository.RoomRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.print.Book;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookingMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "room", source = "room")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    public abstract BookingEntity toEntity(BookingDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "room", source = "room")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    public abstract BookingDto toDto(BookingEntity entity);

    public abstract List<BookingEntity> toListEntity(List<BookingDto> dtos);

    public abstract List<BookingDto> toListDto(List<BookingEntity> entities);
}



