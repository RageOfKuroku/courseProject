package com.example.mainfile.dto;

import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private Integer roomId;
    private Integer hotelId;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private String description;
}
