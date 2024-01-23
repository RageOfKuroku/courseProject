package com.example.mainfile.entity;

import java.util.List;

public class CustomerEntity {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<BookingEntity> bookings;
}
