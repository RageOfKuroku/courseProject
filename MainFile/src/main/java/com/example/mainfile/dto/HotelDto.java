package com.example.mainfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDto {
    private Integer id;
    private String name;
    private String address;
    private Double rating;
    private String description;
    private Integer stars;
    private byte[] imageToShow;

    private List<RoomDto> rooms;
    public String getImageFromBytes() {
        return Base64.getEncoder().encodeToString(imageToShow);
    }
}
