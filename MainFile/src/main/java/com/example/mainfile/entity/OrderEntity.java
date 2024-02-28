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
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private ProductEntity product;

    private Integer quantity;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
}