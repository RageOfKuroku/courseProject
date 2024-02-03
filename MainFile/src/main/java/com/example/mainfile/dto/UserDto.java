package com.example.mainfile.dto;

import com.example.mainfile.entity.CustomerEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    private String username;

    @Email(message = "wrong email")
    @NotBlank(message = "empty email")
    private String email;

    @NotBlank(message = "empty password")
    @Length(max = 15, message = "length of should be < 15")
    private String password;
    private CustomerEntity customer;
}
