package com.example.mainfile.mapper;

import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomId",source = "roomId")
    @Mapping(target = "hotelId",source = "hotelId")
    @Mapping(target = "roomType",source = "roomType")
    @Mapping(target = "roomStatus",source = "roomStatus")
    @Mapping(target = "description",source = "description")
    RoomEntity toEntity(RoomDto dto);

    @Mapping(target = "roomId",source = "roomId")
    @Mapping(target = "hotelId",source = "hotelId")
    @Mapping(target = "roomType",source = "roomType")
    @Mapping(target = "roomStatus",source = "roomStatus")
    @Mapping(target = "description",source = "description")
    RoomDto toDto(RoomEntity entity);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget RoomEntity entity, RoomDto dto);
}
