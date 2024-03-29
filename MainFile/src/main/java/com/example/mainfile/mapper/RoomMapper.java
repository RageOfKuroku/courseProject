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
    @Mapping(target = "roomPrice",source = "roomPrice")
    @Mapping(target = "roomType",source = "roomType")
    @Mapping(target = "roomStatus",source = "roomStatus")
    @Mapping(target = "additions",source = "additions")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "imageToShow", source = "imageToShow")
    RoomEntity toEntity(RoomDto dto);

    @Mapping(target = "roomId",source = "roomId")
    @Mapping(target = "roomPrice",source = "roomPrice")
    @Mapping(target = "roomType",source = "roomType")
    @Mapping(target = "roomStatus",source = "roomStatus")
    @Mapping(target = "additions",source = "additions")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "imageToShow", source = "imageToShow")
    RoomDto toDto(RoomEntity entity);


    @Mapping(target = "roomId", ignore = true)
    void update(@MappingTarget RoomEntity entity, RoomDto dto);

    @Mapping(target = "roomId", ignore = true)
    @Mapping(target = "imageToShow", ignore = true)
    void updateWithoutImage(@MappingTarget RoomEntity entity, RoomDto dto);

    List<RoomEntity> toListEntity(List<RoomDto> dtos);

    List<RoomDto> toListDto(List<RoomEntity> entities);
}
