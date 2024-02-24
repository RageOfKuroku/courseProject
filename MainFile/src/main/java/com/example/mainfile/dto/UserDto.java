package com.example.mainfile.dto;

import com.example.mainfile.model.Role;
import com.example.mainfile.service.BookingService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    BookingService bookingService;



    private UUID id;
    @Email(message = "wrong email")
    @NotBlank(message = "empty email")
    private String email;
    private String emailLogin;
    @NotBlank(message = "empty password")
    @Length(max = 15, message = "length of should be < 15")
    private String password;
    private Role role;
    private String name;
    private String phoneNumber;
    private List<BookingDto> bookings;
    private Integer version;
}

