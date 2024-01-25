package com.example.mainfile.mapper;

import com.example.mainfile.dto.RoomDto;
import com.example.mainfile.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomId",source = "roomId")
    @Mapping(target = "hotelId",source = "hotelId")
    @Mapping(target = "roomPrice",source = "roomPrice")
    @Mapping(target = "roomType",source = "roomType")
    @Mapping(target = "roomStatus",source = "roomStatus")
    @Mapping(target = "additions",source = "additions")
    @Mapping(target = "description",source = "description")
    RoomEntity toEntity(RoomDto dto);

    @Mapping(target = "roomId",source = "roomId")
    @Mapping(target = "hotelId",source = "hotelId")
    @Mapping(target = "roomPrice",source = "roomPrice")
    @Mapping(target = "roomType",source = "roomType")
    @Mapping(target = "roomStatus",source = "roomStatus")
    @Mapping(target = "additions",source = "additions")
    @Mapping(target = "description",source = "description")
    RoomDto toDto(RoomEntity entity);

    @Mapping(target = "roomId", ignore = true)
    void update(@MappingTarget RoomEntity entity, RoomDto dto);

    List<RoomEntity> toListEntity(List<RoomDto> dtos);

    List<RoomDto> toListDto(List<RoomEntity> entities);


}
