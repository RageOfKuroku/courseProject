package com.example.mainfile.mapper;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@Mapper(
        componentModel = "spring"
)
@Getter
public abstract class UserMapper {
    @Autowired
    BCryptPasswordEncoder encoder;

    @Mapping(target = "password", expression = "java(encodePassword(dto))")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfUpdate", ignore = true)
    public abstract UserEntity toEntity(UserDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "version", source = "version")
    public abstract UserDto toDto(UserEntity entity);


    public abstract List<UserEntity> toListEntity(List<UserDto> dtos);

    public abstract List<UserDto> toListDto(List<UserEntity> entities);

    public String encodePassword(UserDto dto) {
        return encoder.encode(dto.getPassword());
    }
}

