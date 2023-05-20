package com.bankInc.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    private String name;

    @OneToMany(mappedBy="product")
    private List<Card> card;

}
