package com.example.mainfile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stores")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String name;
    private String address;
    private Double rating;
    private String description;
    private String website;
    @Lob
    private byte[] logo;
}