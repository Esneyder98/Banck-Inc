package com.bankInc.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    private String name;

    @Column(name = "card_Number")
    private Integer cardNumber;

    @Column(name = "holder_name")
    private String holderName;

    private String state;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    private Integer balance;

    @Column(name = "product_id")
    private Long productId;

    @OneToMany(mappedBy="card")
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}
