package com.example.mainfile.dto;

import com.example.mainfile.entity.BookingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<BookingDto> bookings;

}
