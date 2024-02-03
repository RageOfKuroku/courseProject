package com.example.mainfile.dto;

import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.model.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Integer roomId;
    private Double roomPrice;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private List<String> additions;
    private String description;

    private HotelDto hotel;
}
