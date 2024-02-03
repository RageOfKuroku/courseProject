package com.example.mainfile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;
    private String name;
    private String address;
    private Double rating;
    private String description;
    @Lob
    private byte[] imageToShow;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<RoomEntity> rooms;
}
