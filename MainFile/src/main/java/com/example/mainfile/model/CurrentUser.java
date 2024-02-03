package com.example.mainfile.model;

import com.example.mainfile.entity.UserEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    @Getter
    public static UserEntity entity;

    private CurrentUser() {
    }

    public static void setEntity(UserEntity entity) {
        CurrentUser.entity = entity;
    }
}
