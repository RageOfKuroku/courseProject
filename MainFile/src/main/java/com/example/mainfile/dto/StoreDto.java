package com.example.mainfile.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class StoreDto {
    private Integer storeId;
    private String name;
    private String address;
    private Double rating;
    private String description;
    private byte[] logo;

    private List<ProductDto> products;
    public String getLogoFromBytes() {
        return Base64.getEncoder().encodeToString(logo);
    }
}