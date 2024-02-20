package com.example.mainfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Integer id;
    private UserDto user;
    private RoomDto room;
    private LocalDate startDate;
    private LocalDate endDate;
}


