package com.example.mainfile.entity;

import com.example.mainfile.model.RoomStatus;
import com.example.mainfile.model.RoomType;
import jakarta.persistence.*;
import lombok.*;

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
    private Double roomPrice;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @ElementCollection
    private List<String> additions;
    private String description;
    @ManyToOne
    @JoinColumn(name="hotel_id", nullable=false)
    private HotelEntity hotel;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BookingEntity booking;


}


