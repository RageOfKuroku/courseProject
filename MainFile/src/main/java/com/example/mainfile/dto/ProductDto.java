package com.example.mainfile.dto;

import com.example.mainfile.model.ProductStatus;
import com.example.mainfile.model.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer productId;
    private String productName;
    private Double productPrice;
    private ProductType productType;
    private ProductStatus productStatus;
    private Integer quantity;
    private String description;
    private StoreDto store;
}