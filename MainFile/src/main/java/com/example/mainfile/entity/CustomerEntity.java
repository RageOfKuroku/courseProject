package com.example.mainfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;
    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<BookingEntity> bookings;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

