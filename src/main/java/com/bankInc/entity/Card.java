package com.bankInc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="cards")

public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "card_Number")
    private Long cardNumber;

    @Column(name = "holder_name")
    private String holderName;

    private Boolean state;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    private BigDecimal balance;

    @Column(name = "product_id")
    private Long productId;

    @OneToMany(mappedBy="card")
    private List<Transaction> transactions;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    public Card() {
    }
    public Card(Long cardId, Long cardNumber, String holderName) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.holderName = holderName;
    }

    public Card(Long cardNumber, String holderName, Boolean state, LocalDateTime expirationDate, BigDecimal balance, Long productId) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.state = state;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.productId = productId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", cardNumber=" + cardNumber +
                ", holderName='" + holderName + '\'' +
                ", state=" + state +
                ", expirationDate=" + expirationDate +
                ", balance=" + balance +
                ", productId=" + productId +
                ", transactions=" + transactions +
                ", product=" + product +
                '}';
    }
}
