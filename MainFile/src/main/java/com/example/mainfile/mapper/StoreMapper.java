package com.example.mainfile.mapper;

import com.example.mainfile.dto.StoreDto;
import com.example.mainfile.entity.StoreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(target = "storeId", source = "storeId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "logo", source = "logo")
    StoreEntity toEntity(StoreDto dto);

    @Mapping(target = "storeId", source = "storeId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "logo", source = "logo")
    StoreDto toDto(StoreEntity entity);

    @Mapping(target = "storeId", ignore = true)
    void update(@MappingTarget StoreEntity entity, StoreDto dto);

    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "logo", ignore = true)
    void updateWithoutImage(@MappingTarget StoreEntity entity, StoreDto dto);

    List<StoreEntity> toListEntity(List<StoreDto> dtos);

    List<StoreDto> toListDto(List<StoreEntity> entities);
}