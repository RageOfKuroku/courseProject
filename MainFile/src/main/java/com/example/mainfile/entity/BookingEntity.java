package com.example.mainfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "room_id")
    @ToString.Exclude
    private RoomEntity room;

    private LocalDate startDate;
    private LocalDate endDate;
}


