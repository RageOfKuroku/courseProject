package com.example.mainfile.dto;

import com.example.mainfile.entity.CustomerEntity;
import com.example.mainfile.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Integer id;
    private CustomerDto customer;
    private RoomDto room;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}


