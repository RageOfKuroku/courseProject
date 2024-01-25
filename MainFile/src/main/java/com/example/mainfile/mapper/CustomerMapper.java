package com.example.mainfile.mapper;

import com.example.mainfile.dto.CustomerDto;
import com.example.mainfile.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    CustomerEntity toEntity(CustomerDto dto);
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    CustomerDto toDto(CustomerEntity entity);
}
