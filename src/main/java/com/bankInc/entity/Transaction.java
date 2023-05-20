package com.bankInc.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="Cards")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "card_id")
    private  Long cardId;

    private BigDecimal worth;

    @Column(name = "transactionType")
    private String transactionType;

    @ManyToOne
    @JoinColumn(name = "card_id", insertable = false, updatable = false)
    private Card card;
}
