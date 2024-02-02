package com.example.mainfile.entity;

import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.model.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    private Integer hotelId;
    private Double roomPrice;
    @Enumerated
    private RoomType roomType;
    @Enumerated
    private RoomStatus roomStatus;
    @ElementCollection
    private List<String> additions;
    private String description;
    @ManyToOne
    private HotelEntity hotel;
}
