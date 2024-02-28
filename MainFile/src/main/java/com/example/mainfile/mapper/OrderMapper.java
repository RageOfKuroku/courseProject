package com.example.mainfile.mapper;

import com.example.mainfile.dto.OrderDto;
import com.example.mainfile.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "deliveryDate", source = "deliveryDate")
    public abstract OrderEntity toEntity(OrderDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "deliveryDate", source = "deliveryDate")
    public abstract OrderDto toDto(OrderEntity entity);

    public abstract List<OrderEntity> toListEntity(List<OrderDto> dtos);

    public abstract List<OrderDto> toListDto(List<OrderEntity> entities);
}