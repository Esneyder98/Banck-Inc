package com.bankInc.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="transactions")
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getWorth() {
        return worth;
    }

    public void setWorth(BigDecimal worth) {
        this.worth = worth;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", cardId=" + cardId +
                ", worth=" + worth +
                ", transactionType='" + transactionType + '\'' +
                ", card=" + card +
                '}';
    }
}
