package com.example.mainfile.mapper;

import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testToEntity() {
        UserDto dto = new UserDto();
        dto.setPassword("TestPassword");

        UserEntity entity = userMapper.toEntity(dto);

        assertEquals(dto.getEmail(), entity.getEmail());
    }


    @Test
    public void testToDto() {
        UserEntity entity = new UserEntity();

        UserDto dto = userMapper.toDto(entity);

        assertEquals(entity.getEmail(), dto.getEmail());
    }

    @Test
    public void testToListEntity() {
        UserDto dto = new UserDto();
        dto.setPassword("TestPassword");

        List<UserDto> dtos = Collections.singletonList(dto);

        List<UserEntity> entities = userMapper.toListEntity(dtos);

        assertEquals(dto.getEmail(), entities.get(0).getEmail());
    }

    @Test
    public void testToListDto() {
        UserEntity entity = new UserEntity();

        List<UserEntity> entities = Collections.singletonList(entity);

        List<UserDto> dtos = userMapper.toListDto(entities);

        assertEquals(entity.getEmail(), dtos.get(0).getEmail());
    }



}

