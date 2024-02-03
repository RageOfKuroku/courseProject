package com.example.mainfile.mapper;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "customer", ignore = true)
    UserEntity toEntity(UserDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "customer", ignore = true)
    UserDto toDto(UserEntity entity);

    List<UserEntity> toListEntity(List<UserDto> dtos);

    List<UserDto> toListDto(List<UserEntity> entities);
}
