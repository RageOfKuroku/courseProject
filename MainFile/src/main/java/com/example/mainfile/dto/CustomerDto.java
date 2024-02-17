package com.example.mainfile.dto;

import com.example.mainfile.entity.BookingEntity;
import com.example.mainfile.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private UUID customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private UserEntity user;
    private List<BookingDto> bookings;

}
