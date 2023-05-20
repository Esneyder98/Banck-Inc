package com.bankInc.Dto;


import java.math.BigDecimal;

public class CardDto {

    private Long cardId;
    private Long cardNumber;
    private BigDecimal balance;

    public CardDto(Long cardId, Long cardNumber, BigDecimal balance) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.balance = balance;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "cardId=" + cardId +
                ", cardNumber=" + cardNumber +
                ", balance=" + balance +
                '}';
    }
}
