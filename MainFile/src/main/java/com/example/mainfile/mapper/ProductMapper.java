package com.example.mainfile.mapper;

import com.example.mainfile.dto.ProductDto;
import com.example.mainfile.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productId",source = "productId")
    @Mapping(target = "productName",source = "productName")
    @Mapping(target = "productPrice",source = "productPrice")
    @Mapping(target = "productType",source = "productType")
    @Mapping(target = "productStatus",source = "productStatus")
    @Mapping(target = "quantity",source = "quantity")
    @Mapping(target = "description",source = "description")
    ProductEntity toEntity(ProductDto dto);

    @Mapping(target = "productId",source = "productId")
    @Mapping(target = "productName",source = "productName")
    @Mapping(target = "productPrice",source = "productPrice")
    @Mapping(target = "productType",source = "productType")
    @Mapping(target = "productStatus",source = "productStatus")
    @Mapping(target = "quantity",source = "quantity")
    @Mapping(target = "description",source = "description")
    ProductDto toDto(ProductEntity entity);

    @Mapping(target = "productId", ignore = true)
    void update(@MappingTarget ProductEntity entity, ProductDto dto);

    List<ProductEntity> toListEntity(List<ProductDto> dtos);

    List<ProductDto> toListDto(List<ProductEntity> entities);
}
