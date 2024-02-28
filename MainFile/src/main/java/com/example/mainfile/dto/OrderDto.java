package com.example.mainfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Integer id;
    private UserDto user;
    private ProductDto product;
    private Integer quantity;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
}